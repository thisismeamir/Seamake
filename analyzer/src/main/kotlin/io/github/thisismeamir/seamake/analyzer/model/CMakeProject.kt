package io.github.thisismeamir.seamake.analyzer.model
/**
 * Represents a complete CMake project with all its components
 */


data class CMakeProject(
    val rootDirectory: String,
    val projectName: String?,
    val projectVersion: String?,
    val projectDescription: String?,
    val languages: List<String>,
    val targets: List<Target>,
    val dependencies: List<Dependency>,
    val options: List<Option>,
    val variables: Map<String, String>,
    val subprojects: List<CMakeProject>,
    val cmakeFiles: List<String>,
    val cmakeMinimumVersion: String?
)

