package com.github.thisismeamir.seemake.analyzer.core

import com.github.thisismeamir.seemake.analyzer.model.CMakeProject
import com.github.thisismeamir.seemake.analyzer.model.Dependency
import com.github.thisismeamir.seemake.analyzer.model.Option
import com.github.thisismeamir.seemake.analyzer.model.Target
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
    val targets = mutableMapOf<String, CMakeTarget>()
    val dependencies = mutableListOf<Dependency>()
    val options = mutableListOf<Option>()
    val variables = mutableMapOf<String, String>()
    val subprojects = mutableListOf<CMakeProject>()
    val cmakeFiles = mutableListOf<String>()
    val errors = mutableListOf<String>()

    fun addTarget(cmakeTarget: CMakeTarget) {
        targets[cmakeTarget.name] = cmakeTarget
    }

    fun updateTarget(name: String, updater: (CMakeTarget) -> CMakeTarget) {
        targets[name]?.let { target ->
            targets[name] = updater(target)
        }
    }

    fun addDependency(dependency: Dependency) {
        dependencies.add(dependency)
    }

    fun updateDependency(
        matchCondition: (Dependency) -> Boolean,
        updater: (Dependency) -> Dependency
    ) {
        for (i in dependencies.indices) {
            if (matchCondition(dependencies[i])) {
                dependencies[i] = updater(dependencies[i])
            }
        }
    }
    fun addOption(option: Option) {
        options.add(option)
    }

    fun updateOption(
        matchCondition: (Option) -> Boolean,
        updater: (Option) -> Option
    ) {
        for (i in options.indices) {
            if (matchCondition(options[i])) {
                options[i] = updater(options[i])
            }
        }
    }

    fun setVariable(name: String, value: String) {
        variables[name] = value
    }

    fun getVariable(name: String): String? {
        return variables[name]
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