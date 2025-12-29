package io.github.thisismeamir.seamake.analyzer.model

data class VersionConstraint(
    val operator: String, // "EXACT", "GREATER", "LESS", etc.
    val version: String
)