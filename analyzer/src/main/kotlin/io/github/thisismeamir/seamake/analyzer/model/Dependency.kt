package io.github.thisismeamir.seamake.analyzer.model

/**
 * Represents a dependency (find_package, FetchContent, add_subdirectory, etc.)
 */
data class Dependency(
    val name: String,
    val type: DependencyType,
    val version: String?,
    val versionConstraint: VersionConstraint?,
    val components: List<String>,
    val isRequired: Boolean,
    val isOptional: Boolean,
    val targets: List<String>,
    val variables: Map<String, String>,
    val location: String? = null,
    val gitRepository: String? = null,
    val gitTag: String? = null
)