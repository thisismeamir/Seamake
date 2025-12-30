package com.github.thisismeamir.seemake.analyzer.model

data class VersionConstraint(
    val operator: String, // "EXACT", "GREATER", "LESS", etc.
    val version: String
)