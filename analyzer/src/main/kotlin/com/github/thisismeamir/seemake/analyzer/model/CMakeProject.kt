package com.github.thisismeamir.seemake.analyzer.model

import java.io.File

/**
 * Represents a complete CMake project with all its components
 */


data class CMakeProject(
    val rootDirectory: String,
    val projectName: String?,
    val projectVersion: String?,
    val projectDescription: String?,
    val languages: List<String>,
    val cmakeTargets: List<CMakeTarget>,
    val dependencies: List<Dependency>,
    val options: List<Option>,
    val variables: Map<String, String>,
    val subprojects: List<CMakeProject>,
    val cmakeFiles: List<String>,
    val cmakeMinimumVersion: String?
) {
    // The equality criteria of Two CMakeProjects
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CMakeProject) return false

        if (File(rootDirectory).absolutePath.toString() != File(other.rootDirectory).absolutePath) return false
        if (projectName != other.projectName) return false
        if (projectVersion != other.projectVersion) return false
        if (projectDescription != other.projectDescription) return false
        if (languages.sorted() != other.languages.sorted()) return false
        if (cmakeTargets.sortedBy { it.name } != other.cmakeTargets.sortedBy { it.name }) return false
        if (dependencies.sortedBy { it.name } != other.dependencies.sortedBy { it.name }) return false
        if (options.sortedBy { it.name } != other.options.sortedBy { it.name }) return false
        if (variables != other.variables) return false
        if (subprojects.sortedBy { it.rootDirectory } != other.subprojects.sortedBy { it.rootDirectory }) return false
        if (cmakeFiles.sorted() != other.cmakeFiles.sorted()) return false
        if (cmakeMinimumVersion != other.cmakeMinimumVersion) return false

        return true
    }
}

