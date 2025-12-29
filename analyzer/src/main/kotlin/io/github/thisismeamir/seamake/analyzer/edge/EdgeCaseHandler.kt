package io.github.thisismeamir.seamake.analyzer.edge

import io.github.thisismeamir.seamake.analyzer.parser.CMakeCommandExtractor.CommandInvocation
import io.github.thisismeamir.seamake.analyzer.core.AnalysisContext
import java.io.File

/**
 * Interface for handling edge cases in CMake parsing
 * Implement this to add custom handling for specific CMake patterns
 */
interface EdgeCaseHandler {
    /**
     * Determine if this handler can process the given command
     */
    fun canHandle(command: CommandInvocation): Boolean

    /**
     * Process the command and update the context
     */
    fun handle(command: CommandInvocation, context: AnalysisContext, currentDir: File)

    /**
     * Priority of this handler (higher priority handlers are checked first)
     */
    fun priority(): Int = 0
}

