package io.github.thisismeamir.seamake.analyzer


import io.github.thisismeamir.seamake.analyzer.core.CMakeAnalyzer
import io.github.thisismeamir.seamake.analyzer.edge.*
import io.github.thisismeamir.seamake.analyzer.model.CMakeProject
import io.github.thisismeamir.seamake.analyzer.report.ReportGenerator
import io.github.thisismeamir.seamake.analyzer.report.ReportExporter
import java.io.File

/**
 * Main entry point for the Seamake CMake Analyzer
 */
object Seamake {

    /**
     * Analyze a CMake project with default settings
     */
    fun analyze(rootDirectory: String): CMakeProject {
        return analyze(File(rootDirectory))
    }

    /**
     * Analyze a CMake project with default settings
     */
    fun analyze(rootDirectory: File): CMakeProject {
        val analyzer = CMakeAnalyzer()
        return analyzer.analyze(rootDirectory)
    }

    /**
     * Analyze a CMake project with custom edge case handlers
     */
    fun analyze(
        rootDirectory: File,
        edgeCaseHandlers: List<EdgeCaseHandler>
    ): CMakeProject {
        val analyzer = CMakeAnalyzer(edgeCaseHandlers)
        return analyzer.analyze(rootDirectory)
    }

    /**
     * Analyze and generate a text report
     */
    fun analyzeAndReport(rootDirectory: File): String {
        val project = analyze(rootDirectory)
        val generator = ReportGenerator()
        return generator.generateTextReport(project)
    }

    /**
     * Analyze and save all reports to a directory
     */
    fun analyzeAndSaveReports(
        rootDirectory: File,
        outputDirectory: File,
        formats: Set<ReportFormat> = setOf(
            ReportFormat.TEXT,
            ReportFormat.JSON,
            ReportFormat.SUMMARY,
            ReportFormat.DEPENDENCY_GRAPH
        )
    ) {
        val project = analyze(rootDirectory)
        outputDirectory.mkdirs()

        if (ReportFormat.TEXT in formats) {
            val textFile = File(outputDirectory, "analysis_report.txt")
            ReportExporter.saveTextReport(project, textFile)
            println("Text report saved to: ${textFile.absolutePath}")
        }

        if (ReportFormat.JSON in formats) {
            val jsonFile = File(outputDirectory, "analysis_report.json")
            ReportExporter.saveJsonReport(project, jsonFile)
            println("JSON report saved to: ${jsonFile.absolutePath}")
        }

        if (ReportFormat.SUMMARY in formats) {
            val summaryFile = File(outputDirectory, "summary.txt")
            ReportExporter.saveSummary(project, summaryFile)
            println("Summary saved to: ${summaryFile.absolutePath}")
        }

        if (ReportFormat.DEPENDENCY_GRAPH in formats) {
            val graphFile = File(outputDirectory, "dependencies.dot")
            ReportExporter.saveDependencyGraph(project, graphFile)
            println("Dependency graph saved to: ${graphFile.absolutePath}")
            println("Generate image with: dot -Tpng ${graphFile.name} -o dependencies.png")
        }
    }
}

enum class ReportFormat {
    TEXT,
    JSON,
    SUMMARY,
    DEPENDENCY_GRAPH
}

/**
 * Command-line interface
 */
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        printUsage()
        return
    }

    val rootDir = File(args[0])
    if (!rootDir.exists() || !rootDir.isDirectory) {
        println("Error: Directory does not exist: ${args[0]}")
        return
    }

    val outputDir = if (args.size > 1) File(args[1]) else File("seamake_output")

    println("Seamake CMake Analyzer")
    println("=" .repeat(80))
    println("Analyzing project in: ${rootDir.absolutePath}")
    println()

    try {
        val project = Seamake.analyze(rootDir)

        println("Analysis complete!")
        println()

        // Print summary to console
        val generator = ReportGenerator()
        println(generator.generateSummary(project))
        println()

        // Save all reports
        println("Saving reports to: ${outputDir.absolutePath}")
        Seamake.analyzeAndSaveReports(rootDir, outputDir)

        println()
        println("=" .repeat(80))
        println("Analysis complete! Check the output directory for detailed reports.")

    } catch (e: Exception) {
        println("Error during analysis: ${e.message}")
        e.printStackTrace()
    }
}

private fun printUsage() {
    println("""
        Seamake CMake Analyzer
        
        Usage: seamake <project_directory> [output_directory]
        
        Arguments:
          project_directory  - Root directory of the CMake project (contains CMakeLists.txt)
          output_directory   - Directory to save analysis reports (default: seamake_output)
        
        Example:
          seamake /path/to/project ./reports
          
        Output files:
          - analysis_report.txt  : Comprehensive text report
          - analysis_report.json : JSON format report
          - summary.txt          : Quick summary
          - dependencies.dot     : Dependency graph (Graphviz format)
    """.trimIndent())
}