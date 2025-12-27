package io.github.thisismeamir.seamake.analyzers.parser.models

class CMakeCatalog : Catalog {
    override val dependencies: List<Dependency> get() = listOf()
    override val targets: List<Target> get() = listOf()
    override val options: List<Option> get() = listOf()
}

infix fun CMakeCatalog.addDependency(newDependency: Dependency) : CMakeCatalog {
    this.dependencies.toMutableList().add(
        newDependency
    )
    return this
}

infix fun CMakeCatalog.addTarget(newTarget: Target) : CMakeCatalog {
    this.targets.toMutableList().add(newTarget)
    return this
}

infix fun CMakeCatalog.addOption(option: Option) : CMakeCatalog {
    this.options.toMutableList().add(option)
    return this
}

