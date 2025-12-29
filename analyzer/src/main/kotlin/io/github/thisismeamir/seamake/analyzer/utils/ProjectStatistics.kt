package io.github.thisismeamir.seamake.analyzer.utils

import io.github.thisismeamir.seamake.analyzer.model.CMakeProject
import io.github.thisismeamir.seamake.analyzer.model.DependencyType
import io.github.thisismeamir.seamake.analyzer.model.TargetType


/**
 * Statistics calculator for CMake projects
 */
object ProjectStatistics {

    data class Statistics(
        val totalTargets: Int,
        val executableCount: Int,
        val libraryCount: Int,
        val totalDependencies: Int,
        val externalDependencies: Int,
        val internalDependencies: Int,
        val totalOptions: Int,
        val totalSourceFiles: Int,
        val totalSubprojects: Int,
        val averageTargetComplexity: Double
    )

    fun calculate(project: CMakeProject): Statistics {
        val allProjects = CMakeUtils.flattenProjects(project)
        val allTargets = allProjects.flatMap { it.targets }
        val allDependencies = allProjects.flatMap { it.dependencies }

        val executableCount = allTargets.count { it.type == TargetType.EXECUTABLE }
        val libraryCount = allTargets.count {
            it.type in listOf(TargetType.STATIC_LIBRARY, TargetType.SHARED_LIBRARY, TargetType.MODULE_LIBRARY)
        }

        val externalDeps = allDependencies.count {
            it.type in listOf(DependencyType.FIND_PACKAGE, DependencyType.FETCH_CONTENT, DependencyType.PKG_CONFIG)
        }

        val internalDeps = allDependencies.count { it.type == DependencyType.SUBDIRECTORY }

        val totalSources = allTargets.sumOf { it.sources.size }
        val totalOptions = allProjects.sumOf { it.options.size }

        val avgComplexity = if (allTargets.isNotEmpty()) {
            allTargets.map { target ->
                target.sources.size +
                        target.linkLibraries.size +
                        target.dependencies.size
            }.average()
        } else 0.0

        return Statistics(
            totalTargets = allTargets.size,
            executableCount = executableCount,
            libraryCount = libraryCount,
            totalDependencies = allDependencies.size,
            externalDependencies = externalDeps,
            internalDependencies = internalDeps,
            totalOptions = totalOptions,
            totalSourceFiles = totalSources,
            totalSubprojects = allProjects.size - 1,
            averageTargetComplexity = avgComplexity
        )
    }
}