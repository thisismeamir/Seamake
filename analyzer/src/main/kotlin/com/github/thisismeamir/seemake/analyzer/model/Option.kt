package com.github.thisismeamir.seemake.analyzer.model

/**
 * Represents a CMake option (option() or set() with CACHE)
 */
data class Option(
    val name: String,
    val description: String,
    val defaultValue: String,
    val type: OptionType,
    val possibleValues: List<String>? = null
)