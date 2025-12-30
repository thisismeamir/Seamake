package io.github.thisismeamir.seemake.analyzer.parser


import io.github.thisismeamir.seemake.parser.CMakeLexer
import io.github.thisismeamir.seemake.parser.CMakeParser
import org.antlr.v4.runtime.*
import java.io.File

/**
 * Main parser interface for CMake files
 * Assumes CMakeLexer and CMakeParser are generated from CMake.g4
 */
class CMakeFileParser {

    /**
     * Parse a single CMakeLists.txt file
     */
    fun parse(file: File): CMakeParser.File_Context {
        if (!file.exists() || !file.isFile) {
            throw IllegalArgumentException("File does not exist: ${file.absolutePath}")
        }

        val input = CharStreams.fromPath(file.toPath())
        val lexer = CMakeLexer(input)
        val tokens = CommonTokenStream(lexer)
        val parser = CMakeParser(tokens)

        // Add error listener for better error handling
        parser.removeErrorListeners()
        parser.addErrorListener(ThrowingErrorListener())

        return parser.file_()
    }

    /**
     * Parse CMake content from a string
     */
    fun parseString(content: String): CMakeParser.File_Context {
        val input = CharStreams.fromString(content)
        val lexer = CMakeLexer(input)
        val tokens = CommonTokenStream(lexer)
        val parser = CMakeParser(tokens)

        parser.removeErrorListeners()
        parser.addErrorListener(ThrowingErrorListener())

        return parser.file_()
    }

    private class ThrowingErrorListener : BaseErrorListener() {
        override fun syntaxError(
            recognizer: Recognizer<*, *>?,
            offendingSymbol: Any?,
            line: Int,
            charPositionInLine: Int,
            msg: String?,
            e: RecognitionException?
        ) {
            throw ParseException("Syntax error at line $line:$charPositionInLine - $msg")
        }
    }
}