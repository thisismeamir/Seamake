package io.github.thisismeamir.seamake.analyzer.edge

import io.github.thisismeamir.seamake.analyzer.core.AnalysisContext
import io.github.thisismeamir.seamake.analyzer.model.Dependency
import io.github.thisismeamir.seamake.analyzer.model.DependencyType
import io.github.thisismeamir.seamake.analyzer.model.VersionConstraint
import io.github.thisismeamir.seamake.analyzer.parser.CMakeCommandExtractor
import java.io.File

/**
 * Handler for pkg-config based dependencies
 */
class PkgConfigHandler : EdgeCaseHandler {
    override fun canHandle(command: CMakeCommandExtractor.CommandInvocation): Boolean {
        return command.name == "pkg_check_modules" || command.name == "pkg_search_module"
    }

    override fun handle(command: CMakeCommandExtractor.CommandInvocation, context: AnalysisContext, currentDir: File) {
        if (command.arguments.size < 2) return

        val varPrefix = command.arguments[0]
        val isRequired = command.arguments.contains("REQUIRED")
        val packages = command.arguments.drop(1).filter {
            it.uppercase() != "REQUIRED" && it.uppercase() != "QUIET"
        }

        for (pkg in packages) {
            // Parse version constraints like "package>=1.0"
            val (name, version, constraint) = parsePkgConfigPackage(pkg)

            context.addDependency(
                Dependency(
                    name = name,
                    type = DependencyType.PKG_CONFIG,
                    version = version,
                    versionConstraint = constraint,
                    components = emptyList(),
                    isRequired = isRequired,
                    isOptional = !isRequired,
                    targets = emptyList(),
                    variables = mapOf("PKG_CONFIG_PREFIX" to varPrefix)
                )
            )
        }
    }

    private fun parsePkgConfigPackage(pkg: String): Triple<String, String?, VersionConstraint?> {
        val operators = listOf(">=", "<=", "=", ">", "<")

        for (op in operators) {
            val parts = pkg.split(op, limit = 2)
            if (parts.size == 2) {
                val constraint = when (op) {
                    ">=" -> VersionConstraint("GREATER_EQUAL", parts[1])
                    "<=" -> VersionConstraint("LESS_EQUAL", parts[1])
                    "=" -> VersionConstraint("EXACT", parts[1])
                    ">" -> VersionConstraint("GREATER", parts[1])
                    "<" -> VersionConstraint("LESS", parts[1])
                    else -> null
                }
                return Triple(parts[0], parts[1], constraint)
            }
        }

        return Triple(pkg, null, null)
    }

    override fun priority(): Int = 10
}