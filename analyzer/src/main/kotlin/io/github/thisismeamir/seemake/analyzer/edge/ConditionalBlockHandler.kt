package io.github.thisismeamir.seemake.analyzer.edge

import io.github.thisismeamir.seemake.analyzer.core.AnalysisContext
import io.github.thisismeamir.seemake.analyzer.parser.CMakeCommandExtractor

import java.io.File

/**
 * Handler for conditional blocks (if/elseif/else/endif)
 * This is complex because it requires state tracking across multiple commands
 */
class ConditionalBlockHandler : EdgeCaseHandler {
    private val conditionalStack = mutableListOf<Boolean>()

    override fun canHandle(command: CMakeCommandExtractor.CommandInvocation): Boolean {
        return command.name in setOf("if", "elseif", "else", "endif")
    }

    override fun handle(command: CMakeCommandExtractor.CommandInvocation, context: AnalysisContext, currentDir: File) {
        when (command.name) {
            "if" -> {
                val condition = evaluateCondition(command.arguments, context)
                conditionalStack.add(condition)
            }
            "elseif" -> {
                if (conditionalStack.isNotEmpty()) {
                    conditionalStack[conditionalStack.lastIndex] =
                        evaluateCondition(command.arguments, context)
                }
            }
            "else" -> {
                if (conditionalStack.isNotEmpty()) {
                    conditionalStack[conditionalStack.lastIndex] =
                        !conditionalStack.last()
                }
            }
            "endif" -> {
                if (conditionalStack.isNotEmpty()) {
                    conditionalStack.removeAt(conditionalStack.lastIndex)
                }
            }
        }

        // Store current conditional state
        context.setVariable("_conditional_active",
            if (conditionalStack.isEmpty() || conditionalStack.all { it }) "TRUE" else "FALSE"
        )
    }

    private fun evaluateCondition(args: List<String>, context: AnalysisContext): Boolean {
        // Simplified condition evaluation
        // Real implementation would need full CMake condition logic
        if (args.isEmpty()) return false

        val condition = args.joinToString(" ")

        return when {
            condition.matches(Regex(""".*\bEXISTS\b.*""")) -> true // Assume exists checks pass
            condition.matches(Regex(""".*\bDEFINED\b\s+(\w+).*""")) -> {
                val varName = Regex("""DEFINED\s+(\w+)""").find(condition)?.groupValues?.get(1)
                varName?.let { context.variables.containsKey(it) } ?: false
            }
            condition in context.variables -> context.variables[condition] == "TRUE"
            else -> true // Default to true for unknown conditions
        }
    }

    override fun priority(): Int = 50
}