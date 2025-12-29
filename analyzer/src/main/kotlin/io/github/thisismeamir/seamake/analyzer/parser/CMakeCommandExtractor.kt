package io.github.thisismeamir.seamake.analyzer.parser

import io.github.thisismeamir.seamake.parser.CMakeParser

/**
 * Extract command invocations from parsed CMake tree
 */
class CMakeCommandExtractor {

    data class CommandInvocation(
        val name: String,
        val arguments: List<String>,
        val line: Int,
        val column: Int
    )

    fun extractCommands(fileContext: CMakeParser.File_Context): List<CommandInvocation> {
        val commands = mutableListOf<CommandInvocation>()

        for (invocation in fileContext.command_invocation()) {
            val commandName = invocation.Identifier().text.lowercase()
            val args = extractArguments(invocation)

            commands.add(
                CommandInvocation(
                    name = commandName,
                    arguments = args,
                    line = invocation.start.line,
                    column = invocation.start.charPositionInLine
                )
            )
        }

        return commands
    }

    private fun extractArguments(invocation: CMakeParser.Command_invocationContext): List<String> {
        val args = mutableListOf<String>()

        // Extract single arguments
        for (singleArg in invocation.single_argument()) {
            args.add(extractSingleArgument(singleArg))
        }

        // Extract compound arguments (recursively)
        for (compoundArg in invocation.compound_argument()) {
            args.addAll(extractCompoundArgument(compoundArg))
        }

        return args
    }

    private fun extractSingleArgument(arg: CMakeParser.Single_argumentContext): String {
        return when {
            arg.Identifier() != null -> arg.Identifier().text
            arg.Unquoted_argument() != null -> arg.Unquoted_argument().text
            arg.Quoted_argument() != null -> {
                // Remove surrounding quotes and handle escape sequences
                val text = arg.Quoted_argument().text
                text.substring(1, text.length - 1)
                    .replace("\\n", "\n")
                    .replace("\\t", "\t")
                    .replace("\\r", "\r")
                    .replace("\\;", ";")
                    .replace("\\\\", "\\")
            }
            arg.Bracket_argument() != null -> {
                // Remove bracket delimiters
                val text = arg.Bracket_argument().text
                extractBracketContent(text)
            }
            else -> ""
        }
    }

    private fun extractCompoundArgument(arg: CMakeParser.Compound_argumentContext): List<String> {
        val args = mutableListOf<String>()

        for (singleArg in arg.single_argument()) {
            args.add(extractSingleArgument(singleArg))
        }

        for (compoundArg in arg.compound_argument()) {
            args.addAll(extractCompoundArgument(compoundArg))
        }

        return args
    }

    private fun extractBracketContent(bracketText: String): String {
        // Handle bracket arguments like [=[content]=]
        val startMatch = Regex("""\[=*\[""").find(bracketText) ?: return bracketText
        val endMatch = Regex("""\]=*\]""").findAll(bracketText).lastOrNull() ?: return bracketText

        val startIndex = startMatch.range.last + 1
        val endIndex = endMatch.range.first

        return if (startIndex < endIndex) {
            bracketText.substring(startIndex, endIndex)
        } else {
            ""
        }
    }
}