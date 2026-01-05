package com.github.thisismeamir.seemake.analyzer.model

/**
 * Represents a build target (executable, library, etc.)
 */
data class CMakeTarget(
    val name: String,
    val type: TargetType,
    val sources: List<String>,
    val linkLibraries: List<String>,
    val includeDirectories: List<String>,
    val compileDefinitions: List<String>,
    val compileOptions: List<String>,
    val properties: Map<String, String>,
    val dependencies: List<String>,
    val isImported: Boolean = false,
    val location: String? = null
)