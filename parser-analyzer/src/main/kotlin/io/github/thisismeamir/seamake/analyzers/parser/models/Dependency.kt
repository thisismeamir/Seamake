package io.github.thisismeamir.seamake.analyzers.parser.models

interface Dependency {
    val name: String
    val version: String
    val options: List<String>?
}

