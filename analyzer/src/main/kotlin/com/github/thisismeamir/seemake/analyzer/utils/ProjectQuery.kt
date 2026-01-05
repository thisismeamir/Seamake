package com.github.thisismeamir.seemake.analyzer.utils

import com.github.thisismeamir.seemake.analyzer.model.CMakeProject
import com.github.thisismeamir.seemake.analyzer.model.Dependency
import com.github.thisismeamir.seemake.analyzer.model.DependencyType
import com.github.thisismeamir.seemake.analyzer.model.Option
import com.github.thisismeamir.seemake.analyzer.model.OptionType
import com.github.thisismeamir.seemake.analyzer.model.TargetType
import com.github.thisismeamir.seemake.analyzer.model.CMakeTarget

/**
 * Query builder for filtering project components
 */
class ProjectQuery(private val project: CMakeProject) {

    fun targets(): TargetQuery = TargetQuery(CMakeUtils.getAllTargets(project))

    fun dependencies(): DependencyQuery = DependencyQuery(CMakeUtils.getAllDependencies(project))

    fun options(): OptionQuery = OptionQuery(CMakeUtils.flattenProjects(project).flatMap { it.options })

    class TargetQuery(private val cmakeTargets: List<CMakeTarget>) {
        fun byType(type: TargetType) = TargetQuery(cmakeTargets.filter { it.type == type })

        fun byName(name: String) = cmakeTargets.find { it.name == name }

        fun byNamePattern(pattern: Regex) = TargetQuery(cmakeTargets.filter { it.name.matches(pattern) })

        fun thatLink(library: String) = TargetQuery(
            cmakeTargets.filter { target ->
                target.linkLibraries.any { it.contains(library) }
            }
        )

        fun withSources() = TargetQuery(cmakeTargets.filter { it.sources.isNotEmpty() })

        fun all() = cmakeTargets
    }

    class DependencyQuery(private val dependencies: List<Dependency>) {
        fun byType(type: DependencyType) = DependencyQuery(dependencies.filter { it.type == type })

        fun byName(name: String) = dependencies.find { it.name == name }

        fun required() = DependencyQuery(dependencies.filter { it.isRequired })

        fun optional() = DependencyQuery(dependencies.filter { it.isOptional })

        fun withVersion() = DependencyQuery(dependencies.filter { it.version != null })

        fun all() = dependencies
    }

    class OptionQuery(private val options: List<Option>) {
        fun byType(type: OptionType) = OptionQuery(options.filter { it.type == type })

        fun byName(name: String) = options.find { it.name == name }

        fun enabled() = OptionQuery(options.filter {
            it.defaultValue.equals("ON", ignoreCase = true) ||
                    it.defaultValue.equals("TRUE", ignoreCase = true)
        })

        fun disabled() = OptionQuery(options.filter {
            it.defaultValue.equals("OFF", ignoreCase = true) ||
                    it.defaultValue.equals("FALSE", ignoreCase = true)
        })

        fun all() = options
    }
}