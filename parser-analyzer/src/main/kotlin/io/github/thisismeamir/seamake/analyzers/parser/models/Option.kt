package io.github.thisismeamir.seamake.analyzers.parser.models

interface Option {
    val name: String
    val availableValues: List<String>
    val dependencies: List<Dependency>
}