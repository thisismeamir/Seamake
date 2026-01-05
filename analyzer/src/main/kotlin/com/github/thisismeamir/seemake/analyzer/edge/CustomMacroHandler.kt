package com.github.thisismeamir.seemake.analyzer.edge

import com.github.thisismeamir.seemake.analyzer.core.AnalysisContext
import com.github.thisismeamir.seemake.analyzer.model.TargetType
import com.github.thisismeamir.seemake.analyzer.parser.CMakeCommandExtractor
import com.github.thisismeamir.seemake.analyzer.model.CMakeTarget
import java.io.File

/**
 * Example: Handler for custom macro definitions
 * Handles patterns like: my_add_library(name SOURCES file1.cpp file2.cpp)
 */
class CustomMacroHandler : EdgeCaseHandler {
    private val knownMacros = setOf("my_add_library", "custom_executable")

    override fun canHandle(command: CMakeCommandExtractor.CommandInvocation): Boolean {
        return command.name in knownMacros
    }

    override fun handle(command: CMakeCommandExtractor.CommandInvocation, context: AnalysisContext, currentDir: File) {
        when (command.name) {
            "my_add_library" -> handleMyAddLibrary(command, context)
            "custom_executable" -> handleCustomExecutable(command, context)
        }
    }

    private fun handleMyAddLibrary(command: CMakeCommandExtractor.CommandInvocation, context: AnalysisContext) {
        if (command.arguments.isEmpty()) return

        val name = command.arguments[0]
        val sourcesIndex = command.arguments.indexOfFirst { it.uppercase() == "SOURCES" }
        val sources = if (sourcesIndex != -1) {
            command.arguments.drop(sourcesIndex + 1)
        } else {
            emptyList()
        }

        context.addTarget(CMakeTarget(
            name = name,
            type = TargetType.STATIC_LIBRARY,
            sources = sources,
            linkLibraries = emptyList(),
            includeDirectories = emptyList(),
            compileDefinitions = emptyList(),
            compileOptions = emptyList(),
            properties = mapOf("CUSTOM_MACRO" to "my_add_library"),
            dependencies = emptyList()
        ))
    }

    private fun handleCustomExecutable(command: CMakeCommandExtractor.CommandInvocation, context: AnalysisContext) {
        if (command.arguments.isEmpty()) return

        val name = command.arguments[0]
        val sources = command.arguments.drop(1)

        context.addTarget(CMakeTarget(
            name = name,
            type = TargetType.EXECUTABLE,
            sources = sources,
            linkLibraries = emptyList(),
            includeDirectories = emptyList(),
            compileDefinitions = emptyList(),
            compileOptions = emptyList(),
            properties = mapOf("CUSTOM_MACRO" to "custom_executable"),
            dependencies = emptyList()
        ))
    }

    override fun priority(): Int = 10
}