package com.github.thisismeamir.seemake.analyzer.utils

import com.github.thisismeamir.seemake.analyzer.model.CMakeProject
import com.github.thisismeamir.seemake.analyzer.model.Dependency
import com.github.thisismeamir.seemake.analyzer.model.VersionConstraint
import java.io.File
import com.github.thisismeamir.seemake.analyzer.model.CMakeTarget
/**
 * Utility functions for working with CMake projects
 */
object CMakeUtils {

    /**
     * Find all CMakeLists.txt files in a directory tree
     */
    fun findCMakeFiles(rootDir: File): List<File> {
        val cmakeFiles = mutableListOf<File>()

        fun searchDir(dir: File) {
            if (!dir.isDirectory) return

            val cmakeFile = File(dir, "CMakeLists.txt")
            if (cmakeFile.exists()) {
                cmakeFiles.add(cmakeFile)
            }

            dir.listFiles()?.forEach { file ->
                if (file.isDirectory && !file.name.startsWith(".")) {
                    searchDir(file)
                }
            }
        }

        searchDir(rootDir)
        return cmakeFiles
    }

    /**
     * Resolve a path relative to CMake source directory
     */
    fun resolvePath(path: String, baseDir: File): File {
        return when {
            path.startsWith("\${CMAKE_CURRENT_SOURCE_DIR}") -> {
                File(baseDir, path.removePrefix("\${CMAKE_CURRENT_SOURCE_DIR}/"))
            }
            path.startsWith("\${CMAKE_SOURCE_DIR}") -> {
                // Would need to track the root source dir
                File(baseDir, path.removePrefix("\${CMAKE_SOURCE_DIR}/"))
            }
            path.startsWith("/") -> {
                File(path)
            }
            else -> {
                File(baseDir, path)
            }
        }
    }

    /**
     * Parse a version string into components
     */
    fun parseVersion(version: String): VersionInfo? {
        val parts = version.split(".", "-", "_")
        if (parts.isEmpty()) return null

        return try {
            VersionInfo(
                major = parts.getOrNull(0)?.toIntOrNull() ?: 0,
                minor = parts.getOrNull(1)?.toIntOrNull() ?: 0,
                patch = parts.getOrNull(2)?.toIntOrNull() ?: 0,
                suffix = parts.drop(3).joinToString(".")
            )
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Compare two versions
     * Returns: -1 if v1 < v2, 0 if equal, 1 if v1 > v2
     */
    fun compareVersions(v1: String, v2: String): Int {
        val version1 = parseVersion(v1) ?: return 0
        val version2 = parseVersion(v2) ?: return 0

        return version1.compareTo(version2)
    }

    /**
     * Check if a version satisfies a constraint
     */
    fun versionSatisfiesConstraint(version: String, constraint: VersionConstraint): Boolean {
        val cmp = compareVersions(version, constraint.version)

        return when (constraint.operator.uppercase()) {
            "EXACT" -> cmp == 0
            "GREATER" -> cmp > 0
            "LESS" -> cmp < 0
            "GREATER_EQUAL" -> cmp >= 0
            "LESS_EQUAL" -> cmp <= 0
            else -> true
        }
    }

    /**
     * Extract target name from a library reference
     * Handles cases like "Boost::system" -> "system"
     */
    fun extractTargetName(reference: String): String {
        return when {
            "::" in reference -> reference.substringAfterLast("::")
            else -> reference
        }
    }

    /**
     * Check if a string is a CMake variable reference
     */
    fun isVariableReference(str: String): Boolean {
        return str.matches(Regex("""\$\{[^}]+\}"""))
    }

    /**
     * Extract variable name from reference
     */
    fun extractVariableName(reference: String): String? {
        val match = Regex("""\$\{([^}]+)\}""").find(reference)
        return match?.groupValues?.get(1)
    }

    /**
     * Normalize a path (convert to forward slashes, resolve ..)
     */
    fun normalizePath(path: String): String {
        return File(path).normalize().path.replace("\\", "/")
    }

    /**
     * Check if a string looks like a source file
     */
    fun isSourceFile(filename: String): Boolean {
        val sourceExtensions = setOf(
            "c", "cc", "cpp", "cxx", "c++",
            "h", "hh", "hpp", "hxx", "h++",
            "m", "mm", // Objective-C
            "cu", // CUDA
            "f", "f90", "f95", "f03", // Fortran
            "swift", "rs", "go"
        )

        val ext = filename.substringAfterLast('.', "").lowercase()
        return ext in sourceExtensions
    }

    /**
     * Check if a string looks like a library name
     */
    fun isLibraryName(name: String): Boolean {
        return name.matches(Regex("""lib[a-zA-Z0-9_-]+""")) ||
                name.contains("::") ||
                name.endsWith(".a") ||
                name.endsWith(".so") ||
                name.endsWith(".dylib") ||
                name.endsWith(".lib") ||
                name.endsWith(".dll")
    }

    /**
     * Flatten a project hierarchy into a single list of all projects
     */
    fun flattenProjects(project: CMakeProject): List<CMakeProject> {
        val projects = mutableListOf(project)
        for (subproject in project.subprojects) {
            projects.addAll(flattenProjects(subproject))
        }
        return projects
    }

    /**
     * Get all unique targets from a project and its subprojects
     */
    fun getAllTargets(project: CMakeProject): List<CMakeTarget> {
        return flattenProjects(project).flatMap { it.cmakeTargets }
    }

    /**
     * Get all unique dependencies from a project and its subprojects
     */
    fun getAllDependencies(project: CMakeProject): List<Dependency> {
        return flattenProjects(project).flatMap { it.dependencies }
    }

    /**
     * Build a dependency graph
     */
    fun buildDependencyGraph(project: CMakeProject): Map<String, Set<String>> {
        val graph = mutableMapOf<String, MutableSet<String>>()

        for (target in getAllTargets(project)) {
            val deps = graph.getOrPut(target.name) { mutableSetOf() }
            deps.addAll(target.dependencies)
            deps.addAll(target.linkLibraries.map { extractTargetName(it) })
        }

        return graph
    }

    /**
     * Find circular dependencies
     */
    fun findCircularDependencies(project: CMakeProject): List<List<String>> {
        val graph = buildDependencyGraph(project)
        val cycles = mutableListOf<List<String>>()
        val visited = mutableSetOf<String>()
        val recStack = mutableSetOf<String>()

        fun dfs(node: String, path: MutableList<String>): Boolean {
            visited.add(node)
            recStack.add(node)
            path.add(node)

            for (neighbor in graph[node] ?: emptySet()) {
                if (neighbor !in visited) {
                    if (dfs(neighbor, path)) return true
                } else if (neighbor in recStack) {
                    // Found a cycle
                    val cycleStart = path.indexOf(neighbor)
                    cycles.add(path.subList(cycleStart, path.size))
                    return true
                }
            }

            recStack.remove(node)
            path.removeAt(path.lastIndex)
            return false
        }

        for (node in graph.keys) {
            if (node !in visited) {
                dfs(node, mutableListOf())
            }
        }

        return cycles
    }
}