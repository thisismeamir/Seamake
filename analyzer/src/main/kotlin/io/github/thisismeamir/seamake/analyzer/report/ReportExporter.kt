package io.github.thisismeamir.seamake.analyzer.report

import io.github.thisismeamir.seamake.analyzer.model.CMakeProject
import java.io.File

/**
 * Export utilities for saving reports to files
 */
object ReportExporter {
    fun saveToFile(content: String, file: File) {
        file.parentFile?.mkdirs()
        file.writeText(content)
    }

    fun saveTextReport(project: CMakeProject, outputFile: File) {
        val generator = ReportGenerator()
        val report = generator.generateTextReport(project)
        saveToFile(report, outputFile)
    }

    fun saveJsonReport(project: CMakeProject, outputFile: File) {
        val generator = ReportGenerator()
        val report = generator.generateJsonReport(project)
        saveToFile(report, outputFile)
    }

    fun saveDependencyGraph(project: CMakeProject, outputFile: File) {
        val generator = ReportGenerator()
        val graph = generator.generateDependencyGraph(project)
        saveToFile(graph, outputFile)
    }

    fun saveSummary(project: CMakeProject, outputFile: File) {
        val generator = ReportGenerator()
        val summary = generator.generateSummary(project)
        saveToFile(summary, outputFile)
    }
}