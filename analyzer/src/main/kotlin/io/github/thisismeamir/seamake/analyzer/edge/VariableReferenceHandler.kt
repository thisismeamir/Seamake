package io.github.thisismeamir.seamake.analyzer.edge

import io.github.thisismeamir.seamake.analyzer.core.AnalysisContext
import io.github.thisismeamir.seamake.analyzer.parser.CMakeCommandExtractor
import java.io.File

/**
 * Handler for variable references like ${VARIABLE_NAME}
 */
class VariableReferenceHandler : EdgeCaseHandler {
    private val variablePattern = Regex("""\$\{([^}]+)\}""")

    override fun canHandle(command: CMakeCommandExtractor.CommandInvocation): Boolean {
        return command.arguments.any { it.contains(variablePattern) }
    }

    override fun handle(command: CMakeCommandExtractor.CommandInvocation, context: AnalysisContext, currentDir: File) {
        // Resolve variable references
        val processedArgs = command.arguments.map { arg ->
            resolveVariables(arg, context)
        }

        // Note: This handler doesn't prevent default processing
        // It just pre-processes the arguments
    }

    private fun resolveVariables(arg: String, context: AnalysisContext): String {
        return arg.replace(variablePattern) { matchResult ->
            val varName = matchResult.groupValues[1]
            context.variables[varName] ?: matchResult.value
        }
    }

    override fun priority(): Int = 100 // High priority to resolve variables early
}