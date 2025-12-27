package io.github.thisismeamir.seamake.analyzers.parser.models

interface Catalog {
    val dependencies: List<Dependency>
    val targets: List<Target>
    val options: List<Option>
}