package io.github.thisismeamir.seemake.analyzer.edge

import io.github.thisismeamir.seemake.analyzer.core.AnalysisContext
import io.github.thisismeamir.seemake.analyzer.parser.CMakeCommandExtractor
import java.io.File

/**
 * Handler for generator expressions like $<CONFIG:Debug>
 */
class GeneratorExpressionHandler : EdgeCaseHandler {
    private val generatorExpressionPattern = Regex("""\$<[^>]+>""")

    override fun canHandle(command: CMakeCommandExtractor.CommandInvocation): Boolean {
        return command.arguments.any { it.contains(generatorExpressionPattern) }
    }

    override fun handle(command: CMakeCommandExtractor.CommandInvocation, context: AnalysisContext, currentDir: File) {
        // Process generator expressions - this is a simplified version
        // In reality, you'd want to evaluate or preserve these for later processing

        val processedArgs = command.arguments.map { arg ->
            processGeneratorExpression(arg)
        }

        // Create a modified command with processed arguments
        val processedCommand = command.copy(arguments = processedArgs)

        // Store the original generator expressions for reference
        context.setVariable("_last_generator_expressions",
            command.arguments.filter { it.contains(generatorExpressionPattern) }.joinToString(";")
        )
    }

    private fun processGeneratorExpression(arg: String): String {
        // Simplified processing - in reality, you'd evaluate based on context
        return arg.replace(generatorExpressionPattern) { matchResult ->
            // Extract and process the expression
            val expr = matchResult.value
            when {
                expr.startsWith("\$<CONFIG:") -> "Debug" // Default to Debug
                expr.startsWith("\$<PLATFORM_ID:") -> "Linux" // Default to Linux
                else -> expr // Keep as-is for unknown expressions
            }
        }
    }

    override fun priority(): Int = 5
}