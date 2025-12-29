package io.github.thisismeamir.seamake.analyzer.report

import io.github.thisismeamir.seamake.analyzer.model.CMakeProject
import io.github.thisismeamir.seamake.analyzer.model.Dependency
import io.github.thisismeamir.seamake.analyzer.model.DependencyType
import io.github.thisismeamir.seamake.analyzer.model.Option
import io.github.thisismeamir.seamake.analyzer.model.TargetType
import io.github.thisismeamir.seamake.analyzer.model.Target
import kotlin.collections.iterator

/**
 * Generates various reports from analyzed CMake projects
 */
class ReportGenerator {

    /**
     * Generate a comprehensive text report
     */
    fun generateTextReport(project: CMakeProject): String {
        val sb = StringBuilder()

        sb.appendLine("=" .repeat(80))
        sb.appendLine("CMake Project Analysis Report")
        sb.appendLine("=" .repeat(80))
        sb.appendLine()

        // Project Information
        sb.appendLine("PROJECT INFORMATION")
        sb.appendLine("-" .repeat(80))
        sb.appendLine("Name: ${project.projectName ?: "N/A"}")
        sb.appendLine("Version: ${project.projectVersion ?: "N/A"}")
        sb.appendLine("Description: ${project.projectDescription ?: "N/A"}")
        sb.appendLine("Root Directory: ${project.rootDirectory}")
        sb.appendLine("CMake Minimum Version: ${project.cmakeMinimumVersion ?: "N/A"}")
        sb.appendLine("Languages: ${project.languages.joinToString(", ")}")
        sb.appendLine()

        // Targets
        sb.appendLine("TARGETS (${project.targets.size})")
        sb.appendLine("-" .repeat(80))
        for (target in project.targets) {
            sb.appendLine()
            sb.appendLine("Target: ${target.name}")
            sb.appendLine("  Type: ${target.type}")
            sb.appendLine("  Sources: ${target.sources.size} file(s)")
            if (target.sources.isNotEmpty()) {
                target.sources.forEach { sb.appendLine("    - $it") }
            }
            if (target.linkLibraries.isNotEmpty()) {
                sb.appendLine("  Link Libraries:")
                target.linkLibraries.forEach { sb.appendLine("    - $it") }
            }
            if (target.includeDirectories.isNotEmpty()) {
                sb.appendLine("  Include Directories:")
                target.includeDirectories.forEach { sb.appendLine("    - $it") }
            }
            if (target.compileDefinitions.isNotEmpty()) {
                sb.appendLine("  Compile Definitions:")
                target.compileDefinitions.forEach { sb.appendLine("    - $it") }
            }
            if (target.compileOptions.isNotEmpty()) {
                sb.appendLine("  Compile Options:")
                target.compileOptions.forEach { sb.appendLine("    - $it") }
            }
            if (target.dependencies.isNotEmpty()) {
                sb.appendLine("  Dependencies:")
                target.dependencies.forEach { sb.appendLine("    - $it") }
            }
            if (target.properties.isNotEmpty()) {
                sb.appendLine("  Properties:")
                target.properties.forEach { (k, v) -> sb.appendLine("    $k = $v") }
            }
        }
        sb.appendLine()

        // Dependencies
        sb.appendLine("DEPENDENCIES (${project.dependencies.size})")
        sb.appendLine("-" .repeat(80))
        for (dep in project.dependencies) {
            sb.appendLine()
            sb.appendLine("Dependency: ${dep.name}")
            sb.appendLine("  Type: ${dep.type}")
            if (dep.version != null) {
                sb.appendLine("  Version: ${dep.version}")
            }
            if (dep.versionConstraint != null) {
                sb.appendLine("  Constraint: ${dep.versionConstraint.operator} ${dep.versionConstraint.version}")
            }
            if (dep.components.isNotEmpty()) {
                sb.appendLine("  Components: ${dep.components.joinToString(", ")}")
            }
            sb.appendLine("  Required: ${dep.isRequired}")
            sb.appendLine("  Optional: ${dep.isOptional}")
            if (dep.gitRepository != null) {
                sb.appendLine("  Git Repository: ${dep.gitRepository}")
            }
            if (dep.gitTag != null) {
                sb.appendLine("  Git Tag: ${dep.gitTag}")
            }
            if (dep.location != null) {
                sb.appendLine("  Location: ${dep.location}")
            }
            if (dep.targets.isNotEmpty()) {
                sb.appendLine("  Targets: ${dep.targets.joinToString(", ")}")
            }
        }
        sb.appendLine()

        // Options
        sb.appendLine("OPTIONS (${project.options.size})")
        sb.appendLine("-" .repeat(80))
        for (option in project.options) {
            sb.appendLine()
            sb.appendLine("Option: ${option.name}")
            sb.appendLine("  Type: ${option.type}")
            sb.appendLine("  Description: ${option.description}")
            sb.appendLine("  Default Value: ${option.defaultValue}")
            if (option.possibleValues != null) {
                sb.appendLine("  Possible Values: ${option.possibleValues.joinToString(", ")}")
            }
        }
        sb.appendLine()

        // Variables
        if (project.variables.isNotEmpty()) {
            sb.appendLine("VARIABLES (${project.variables.size})")
            sb.appendLine("-" .repeat(80))
            for ((key, value) in project.variables) {
                sb.appendLine("$key = $value")
            }
            sb.appendLine()
        }

        // Subprojects
        if (project.subprojects.isNotEmpty()) {
            sb.appendLine("SUBPROJECTS (${project.subprojects.size})")
            sb.appendLine("-" .repeat(80))
            for (subproject in project.subprojects) {
                sb.appendLine("- ${subproject.projectName ?: subproject.rootDirectory}")
                sb.appendLine("  Directory: ${subproject.rootDirectory}")
                sb.appendLine("  Targets: ${subproject.targets.size}")
                sb.appendLine("  Dependencies: ${subproject.dependencies.size}")
            }
            sb.appendLine()
        }

        // CMake Files
        sb.appendLine("CMAKE FILES (${project.cmakeFiles.size})")
        sb.appendLine("-" .repeat(80))
        for (file in project.cmakeFiles) {
            sb.appendLine("- $file")
        }
        sb.appendLine()

        sb.appendLine("=" .repeat(80))
        sb.appendLine("End of Report")
        sb.appendLine("=" .repeat(80))

        return sb.toString()
    }

    /**
     * Generate a JSON report
     */
    fun generateJsonReport(project: CMakeProject): String {
        return buildString {
            appendLine("{")
            appendLine("  \"projectName\": ${project.projectName?.toJsonString() ?: "null"},")
            appendLine("  \"projectVersion\": ${project.projectVersion?.toJsonString() ?: "null"},")
            appendLine("  \"projectDescription\": ${project.projectDescription?.toJsonString() ?: "null"},")
            appendLine("  \"rootDirectory\": ${project.rootDirectory.toJsonString()},")
            appendLine("  \"cmakeMinimumVersion\": ${project.cmakeMinimumVersion?.toJsonString() ?: "null"},")
            appendLine("  \"languages\": ${project.languages.toJsonArray()},")
            appendLine("  \"targets\": [")
            project.targets.forEachIndexed { index, target ->
                appendLine("    ${targetToJson(target)}${if (index < project.targets.size - 1) "," else ""}")
            }
            appendLine("  ],")
            appendLine("  \"dependencies\": [")
            project.dependencies.forEachIndexed { index, dep ->
                appendLine("    ${dependencyToJson(dep)}${if (index < project.dependencies.size - 1) "," else ""}")
            }
            appendLine("  ],")
            appendLine("  \"options\": [")
            project.options.forEachIndexed { index, option ->
                appendLine("    ${optionToJson(option)}${if (index < project.options.size - 1) "," else ""}")
            }
            appendLine("  ],")
            appendLine("  \"variables\": ${project.variables.toJsonObject()},")
            appendLine("  \"cmakeFiles\": ${project.cmakeFiles.toJsonArray()},")
            appendLine("  \"subprojects\": [")
            project.subprojects.forEachIndexed { index, sub ->
                append("    ${generateJsonReport(sub).lines().joinToString("\n    ")}")
                if (index < project.subprojects.size - 1) appendLine(",")
            }
            appendLine("  ]")
            appendLine("}")
        }
    }

    /**
     * Generate a dependency graph in DOT format (for Graphviz)
     */
    fun generateDependencyGraph(project: CMakeProject): String {
        val sb = StringBuilder()

        sb.appendLine("digraph CMakeDependencies {")
        sb.appendLine("  rankdir=LR;")
        sb.appendLine("  node [shape=box];")
        sb.appendLine()

        // Add project node
        val projectId = sanitizeId(project.projectName ?: "root")
        sb.appendLine("  \"$projectId\" [label=\"${project.projectName ?: "Project"}\", style=filled, fillcolor=lightblue];")
        sb.appendLine()

        // Add target nodes
        for (target in project.targets) {
            val targetId = sanitizeId("target_${target.name}")
            val color = when (target.type) {
                TargetType.EXECUTABLE -> "lightgreen"
                TargetType.SHARED_LIBRARY, TargetType.STATIC_LIBRARY -> "lightyellow"
                else -> "white"
            }
            sb.appendLine("  \"$targetId\" [label=\"${target.name}\\n(${target.type})\", style=filled, fillcolor=$color];")
            sb.appendLine("  \"$projectId\" -> \"$targetId\";")

            // Add dependencies
            for (dep in target.dependencies) {
                val depId = sanitizeId("target_$dep")
                sb.appendLine("  \"$targetId\" -> \"$depId\" [label=\"depends\"];")
            }

            // Add link libraries
            for (lib in target.linkLibraries) {
                val libId = sanitizeId("lib_$lib")
                sb.appendLine("  \"$libId\" [label=\"$lib\", shape=ellipse];")
                sb.appendLine("  \"$targetId\" -> \"$libId\" [label=\"links\"];")
            }
        }
        sb.appendLine()

        // Add dependency nodes
        for (dep in project.dependencies) {
            val depId = sanitizeId("dep_${dep.name}")
            val color = when (dep.type) {
                DependencyType.FIND_PACKAGE -> "lightcoral"
                DependencyType.FETCH_CONTENT -> "lightpink"
                DependencyType.SUBDIRECTORY -> "lightgray"
                else -> "white"
            }
            sb.appendLine("  \"$depId\" [label=\"${dep.name}\\n(${dep.type})\", style=filled, fillcolor=$color];")
            sb.appendLine("  \"$projectId\" -> \"$depId\";")
        }

        sb.appendLine("}")

        return sb.toString()
    }

    /**
     * Generate a summary report
     */
    fun generateSummary(project: CMakeProject): String {
        return buildString {
            appendLine("CMake Project Summary")
            appendLine("=" .repeat(50))
            appendLine("Project: ${project.projectName ?: "N/A"}")
            appendLine("Version: ${project.projectVersion ?: "N/A"}")
            appendLine()
            appendLine("Statistics:")
            appendLine("  - Targets: ${project.targets.size}")
            appendLine("    - Executables: ${project.targets.count { it.type == TargetType.EXECUTABLE }}")
            appendLine("    - Libraries: ${project.targets.count { it.type in listOf(TargetType.STATIC_LIBRARY, TargetType.SHARED_LIBRARY) }}")
            appendLine("  - Dependencies: ${project.dependencies.size}")
            appendLine("    - find_package: ${project.dependencies.count { it.type == DependencyType.FIND_PACKAGE }}")
            appendLine("    - FetchContent: ${project.dependencies.count { it.type == DependencyType.FETCH_CONTENT }}")
            appendLine("    - Subdirectories: ${project.dependencies.count { it.type == DependencyType.SUBDIRECTORY }}")
            appendLine("  - Options: ${project.options.size}")
            appendLine("  - Subprojects: ${project.subprojects.size}")
            appendLine("  - CMake Files: ${project.cmakeFiles.size}")
        }
    }

    private fun targetToJson(target: Target): String {
        return buildString {
            append("{")
            append("\"name\": ${target.name.toJsonString()}, ")
            append("\"type\": \"${target.type}\", ")
            append("\"sources\": ${target.sources.toJsonArray()}, ")
            append("\"linkLibraries\": ${target.linkLibraries.toJsonArray()}, ")
            append("\"includeDirectories\": ${target.includeDirectories.toJsonArray()}, ")
            append("\"compileDefinitions\": ${target.compileDefinitions.toJsonArray()}, ")
            append("\"compileOptions\": ${target.compileOptions.toJsonArray()}, ")
            append("\"properties\": ${target.properties.toJsonObject()}, ")
            append("\"dependencies\": ${target.dependencies.toJsonArray()}")
            append("}")
        }
    }

    private fun dependencyToJson(dep: Dependency): String {
        return buildString {
            append("{")
            append("\"name\": ${dep.name.toJsonString()}, ")
            append("\"type\": \"${dep.type}\", ")
            append("\"version\": ${dep.version?.toJsonString() ?: "null"}, ")
            append("\"components\": ${dep.components.toJsonArray()}, ")
            append("\"isRequired\": ${dep.isRequired}, ")
            append("\"isOptional\": ${dep.isOptional}, ")
            append("\"gitRepository\": ${dep.gitRepository?.toJsonString() ?: "null"}, ")
            append("\"gitTag\": ${dep.gitTag?.toJsonString() ?: "null"}")
            append("}")
        }
    }

    private fun optionToJson(option: Option): String {
        return buildString {
            append("{")
            append("\"name\": ${option.name.toJsonString()}, ")
            append("\"type\": \"${option.type}\", ")
            append("\"description\": ${option.description.toJsonString()}, ")
            append("\"defaultValue\": ${option.defaultValue.toJsonString()}")
            append("}")
        }
    }

    private fun String.toJsonString(): String {
        return "\"${this.replace("\\", "\\\\").replace("\"", "\\\"")}\""
    }

    private fun List<String>.toJsonArray(): String {
        return "[${this.joinToString(", ") { it.toJsonString() }}]"
    }

    private fun Map<String, String>.toJsonObject(): String {
        if (isEmpty()) return "{}"
        return "{${entries.joinToString(", ") { (k, v) -> "${k.toJsonString()}: ${v.toJsonString()}" }}}"
    }

    private fun sanitizeId(id: String): String {
        return id.replace(Regex("[^a-zA-Z0-9_]"), "_")
    }
}