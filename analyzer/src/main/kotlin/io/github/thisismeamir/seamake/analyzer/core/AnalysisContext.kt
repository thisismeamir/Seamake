package io.github.thisismeamir.seamake.analyzer.core

import io.github.thisismeamir.seamake.analyzer.model.CMakeProject
import io.github.thisismeamir.seamake.analyzer.model.Dependency
import io.github.thisismeamir.seamake.analyzer.model.Option
import io.github.thisismeamir.seamake.analyzer.model.Target
import java.io.File

/**
 * Internal context for building the project model
 */
class AnalysisContext(val rootDirectory: File) {
    var projectName: String? = null
    var projectVersion: String? = null
    var projectDescription: String? = null
    var cmakeMinimumVersion: String? = null

    val languages = mutableListOf<String>()
    val targets = mutableMapOf<String, Target>()
    val dependencies = mutableListOf<Dependency>()
    val options = mutableListOf<Option>()
    val variables = mutableMapOf<String, String>()
    val subprojects = mutableListOf<CMakeProject>()
    val cmakeFiles = mutableListOf<String>()
    val errors = mutableListOf<String>()

    fun addTarget(target: Target) {
        targets[target.name] = target
    }

    fun updateTarget(name: String, updater: (Target) -> Target) {
        targets[name]?.let { target ->
            targets[name] = updater(target)
        }
    }

    fun addDependency(dependency: Dependency) {
        dependencies.add(dependency)
    }

    fun addOption(option: Option) {
        options.add(option)
    }

    fun setVariable(name: String, value: String) {
        variables[name] = value
    }

    fun addSubproject(subproject: CMakeProject) {
        subprojects.add(subproject)
    }

    fun addCMakeFile(path: String) {
        cmakeFiles.add(path)
    }

    fun addError(error: String) {
        errors.add(error)
    }
}