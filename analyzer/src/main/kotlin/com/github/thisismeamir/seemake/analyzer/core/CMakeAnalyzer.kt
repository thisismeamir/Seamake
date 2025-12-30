package com.github.thisismeamir.seemake.analyzer.core


import com.github.thisismeamir.seemake.analyzer.model.*
import com.github.thisismeamir.seemake.analyzer.parser.CMakeCommandExtractor.CommandInvocation
import com.github.thisismeamir.seemake.analyzer.parser.CMakeFileParser
import com.github.thisismeamir.seemake.analyzer.parser.CMakeCommandExtractor
import com.github.thisismeamir.seemake.analyzer.edge.EdgeCaseHandler
import java.io.File

/**
 * Main analyzer that processes CMake files and builds the project model
 */
class CMakeAnalyzer(
    private val edgeCaseHandlers: List<EdgeCaseHandler> = emptyList()
) {

    private val parser = CMakeFileParser()
    private val commandExtractor = CMakeCommandExtractor()

    /**
     * Analyze a CMake project starting from root directory
     */
    fun analyze(rootDirectory: File): CMakeProject {
        if (!rootDirectory.exists() || !rootDirectory.isDirectory) {
            throw IllegalArgumentException("Root directory does not exist: ${rootDirectory.absolutePath}")
        }

        val cmakeFile = File(rootDirectory, "CMakeLists.txt")
        if (!cmakeFile.exists()) {
            throw IllegalArgumentException("CMakeLists.txt not found in: ${rootDirectory.absolutePath}")
        }

        val context = AnalysisContext(rootDirectory)
        analyzeCMakeFile(cmakeFile, context)

        return buildProject(context)
    }

    private fun analyzeCMakeFile(file: File, context: AnalysisContext) {
        try {
            val parseTree = parser.parse(file)
            val commands = commandExtractor.extractCommands(parseTree)

            context.addCMakeFile(file.absolutePath)

            for (command in commands) {
                processCommand(command, context, file.parentFile)
            }
        } catch (e: Exception) {
            context.addError("Error parsing ${file.absolutePath}: ${e.message}")
        }
    }

    private fun processCommand(command: CommandInvocation, context: AnalysisContext, currentDir: File) {
        // Apply edge case handlers first
        for (handler in edgeCaseHandlers) {
            if (handler.canHandle(command)) {
                handler.handle(command, context, currentDir)
                return
            }
        }

        // Default command processing
        when (command.name) {
            "project" -> handleProject(command, context)
            "cmake_minimum_required" -> handleCMakeMinimumRequired(command, context)
            "add_executable" -> handleAddExecutable(command, context)
            "add_library" -> handleAddLibrary(command, context)
            "target_link_libraries" -> handleTargetLinkLibraries(command, context)
            "target_include_directories" -> handleTargetIncludeDirectories(command, context)
            "target_compile_definitions" -> handleTargetCompileDefinitions(command, context)
            "target_compile_options" -> handleTargetCompileOptions(command, context)
            "find_package" -> handleFindPackage(command, context)
            "fetchcontent_declare" -> handleFetchContentDeclare(command, context)
            "add_subdirectory" -> handleAddSubdirectory(command, context, currentDir)
            "option" -> handleOption(command, context)
            "set" -> handleSet(command, context)
            "enable_language" -> handleEnableLanguage(command, context)
            "include" -> handleInclude(command, context, currentDir)
            "target_sources" -> handleTargetSources(command, context)
            "set_target_properties" -> handleSetTargetProperties(command, context)
            "add_dependencies" -> handleAddDependencies(command, context)
        }
    }

    private fun handleProject(command: CommandInvocation, context: AnalysisContext) {
        if (command.arguments.isEmpty()) return

        context.projectName = command.arguments[0]

        var i = 1
        while (i < command.arguments.size) {
            when (command.arguments[i].uppercase()) {
                "VERSION" -> {
                    if (i + 1 < command.arguments.size) {
                        context.projectVersion = command.arguments[i + 1]
                        i += 2
                    } else i++
                }
                "DESCRIPTION" -> {
                    if (i + 1 < command.arguments.size) {
                        context.projectDescription = command.arguments[i + 1]
                        i += 2
                    } else i++
                }
                "LANGUAGES" -> {
                    i++
                    while (i < command.arguments.size && !isKeyword(command.arguments[i])) {
                        context.languages.add(command.arguments[i])
                        i++
                    }
                }
                else -> {
                    if (!isKeyword(command.arguments[i])) {
                        context.languages.add(command.arguments[i])
                    }
                    i++
                }
            }
        }
    }

    private fun handleCMakeMinimumRequired(command: CommandInvocation, context: AnalysisContext) {
        val versionIndex = command.arguments.indexOfFirst { it.uppercase() == "VERSION" }
        if (versionIndex != -1 && versionIndex + 1 < command.arguments.size) {
            context.cmakeMinimumVersion = command.arguments[versionIndex + 1]
        }
    }

    private fun handleAddExecutable(command: CommandInvocation, context: AnalysisContext) {
        if (command.arguments.isEmpty()) return

        val name = command.arguments[0]
        val sources = command.arguments.drop(1).filter { !isKeyword(it) }

        context.addTarget(Target(
            name = name,
            type = TargetType.EXECUTABLE,
            sources = sources,
            linkLibraries = emptyList(),
            includeDirectories = emptyList(),
            compileDefinitions = emptyList(),
            compileOptions = emptyList(),
            properties = emptyMap(),
            dependencies = emptyList()
        ))
    }

    private fun handleAddLibrary(command: CommandInvocation, context: AnalysisContext) {
        if (command.arguments.isEmpty()) return

        val name = command.arguments[0]
        var type = TargetType.STATIC_LIBRARY
        var startIndex = 1

        if (command.arguments.size > 1) {
            when (command.arguments[1].uppercase()) {
                "STATIC" -> {
                    type = TargetType.STATIC_LIBRARY
                    startIndex = 2
                }
                "SHARED" -> {
                    type = TargetType.SHARED_LIBRARY
                    startIndex = 2
                }
                "MODULE" -> {
                    type = TargetType.MODULE_LIBRARY
                    startIndex = 2
                }
                "OBJECT" -> {
                    type = TargetType.OBJECT_LIBRARY
                    startIndex = 2
                }
                "INTERFACE" -> {
                    type = TargetType.INTERFACE_LIBRARY
                    startIndex = 2
                }
            }
        }

        val sources = command.arguments.drop(startIndex).filter { !isKeyword(it) }

        context.addTarget(Target(
            name = name,
            type = type,
            sources = sources,
            linkLibraries = emptyList(),
            includeDirectories = emptyList(),
            compileDefinitions = emptyList(),
            compileOptions = emptyList(),
            properties = emptyMap(),
            dependencies = emptyList()
        ))
    }

    private fun handleTargetLinkLibraries(command: CommandInvocation, context: AnalysisContext) {
        if (command.arguments.isEmpty()) return

        val targetName = command.arguments[0]
        val libraries = command.arguments.drop(1).filter {
            !it.uppercase().matches(Regex("PUBLIC|PRIVATE|INTERFACE"))
        }

        context.updateTarget(targetName) { target ->
            target.copy(linkLibraries = target.linkLibraries + libraries)
        }
    }

    private fun handleTargetIncludeDirectories(command: CommandInvocation, context: AnalysisContext) {
        if (command.arguments.isEmpty()) return

        val targetName = command.arguments[0]
        val directories = command.arguments.drop(1).filter {
            !it.uppercase().matches(Regex("PUBLIC|PRIVATE|INTERFACE|SYSTEM|BEFORE|AFTER"))
        }

        context.updateTarget(targetName) { target ->
            target.copy(includeDirectories = target.includeDirectories + directories)
        }
    }

    private fun handleTargetCompileDefinitions(command: CommandInvocation, context: AnalysisContext) {
        if (command.arguments.isEmpty()) return

        val targetName = command.arguments[0]
        val definitions = command.arguments.drop(1).filter {
            !it.uppercase().matches(Regex("PUBLIC|PRIVATE|INTERFACE"))
        }

        context.updateTarget(targetName) { target ->
            target.copy(compileDefinitions = target.compileDefinitions + definitions)
        }
    }

    private fun handleTargetCompileOptions(command: CommandInvocation, context: AnalysisContext) {
        if (command.arguments.isEmpty()) return

        val targetName = command.arguments[0]
        val options = command.arguments.drop(1).filter {
            !it.uppercase().matches(Regex("PUBLIC|PRIVATE|INTERFACE|BEFORE"))
        }

        context.updateTarget(targetName) { target ->
            target.copy(compileOptions = target.compileOptions + options)
        }
    }

    private fun handleFindPackage(command: CommandInvocation, context: AnalysisContext) {
        if (command.arguments.isEmpty()) return

        val name = command.arguments[0]
        var version: String? = null
        var versionConstraint: VersionConstraint? = null
        val components = mutableListOf<String>()
        var isRequired = false
        var isOptional = false

        var i = 1
        while (i < command.arguments.size) {
            when (command.arguments[i].uppercase()) {
                "REQUIRED" -> {
                    isRequired = true
                    i++
                }
                "OPTIONAL" -> {
                    isOptional = true
                    i++
                }
                "EXACT" -> {
                    if (version != null) {
                        versionConstraint = VersionConstraint("EXACT", version)
                    }
                    i++
                }
                "COMPONENTS" -> {
                    i++
                    while (i < command.arguments.size && !isKeyword(command.arguments[i])) {
                        components.add(command.arguments[i])
                        i++
                    }
                }
                else -> {
                    // Check if it's a version number
                    if (version == null && command.arguments[i].matches(Regex("""[\d.]+"""))) {
                        version = command.arguments[i]
                    }
                    i++
                }
            }
        }

        context.addDependency(Dependency(
            name = name,
            type = DependencyType.FIND_PACKAGE,
            version = version,
            versionConstraint = versionConstraint,
            components = components,
            isRequired = isRequired,
            isOptional = isOptional,
            targets = emptyList(),
            variables = emptyMap()
        ))
    }

    private fun handleFetchContentDeclare(command: CommandInvocation, context: AnalysisContext) {
        if (command.arguments.isEmpty()) return

        val name = command.arguments[0]
        var gitRepo: String? = null
        var gitTag: String? = null

        var i = 1
        while (i < command.arguments.size) {
            when (command.arguments[i].uppercase()) {
                "GIT_REPOSITORY" -> {
                    if (i + 1 < command.arguments.size) {
                        gitRepo = command.arguments[i + 1]
                        i += 2
                    } else i++
                }
                "GIT_TAG" -> {
                    if (i + 1 < command.arguments.size) {
                        gitTag = command.arguments[i + 1]
                        i += 2
                    } else i++
                }
                else -> i++
            }
        }

        context.addDependency(Dependency(
            name = name,
            type = DependencyType.FETCH_CONTENT,
            version = gitTag,
            versionConstraint = null,
            components = emptyList(),
            isRequired = false,
            isOptional = false,
            targets = emptyList(),
            variables = emptyMap(),
            gitRepository = gitRepo,
            gitTag = gitTag
        ))
    }

    private fun handleAddSubdirectory(command: CommandInvocation, context: AnalysisContext, currentDir: File) {
        if (command.arguments.isEmpty()) return

        val subdirPath = command.arguments[0]
        val subdirFile = File(currentDir, subdirPath)
        val cmakeFile = File(subdirFile, "CMakeLists.txt")

        if (cmakeFile.exists()) {
            val subContext = AnalysisContext(subdirFile)
            analyzeCMakeFile(cmakeFile, subContext)
            context.addSubproject(buildProject(subContext))
        }

        context.addDependency(Dependency(
            name = subdirPath,
            type = DependencyType.SUBDIRECTORY,
            version = null,
            versionConstraint = null,
            components = emptyList(),
            isRequired = false,
            isOptional = false,
            targets = emptyList(),
            variables = emptyMap(),
            location = subdirFile.absolutePath
        ))
    }

    private fun handleOption(command: CommandInvocation, context: AnalysisContext) {
        if (command.arguments.size < 2) return

        val name = command.arguments[0]
        val description = command.arguments[1]
        val defaultValue = if (command.arguments.size > 2) command.arguments[2] else "OFF"

        context.addOption(Option(
            name = name,
            description = description,
            defaultValue = defaultValue,
            type = OptionType.BOOL
        ))
    }

    private fun handleSet(command: CommandInvocation, context: AnalysisContext) {
        if (command.arguments.isEmpty()) return

        val name = command.arguments[0]
        val cacheIndex = command.arguments.indexOfFirst { it.uppercase() == "CACHE" }

        if (cacheIndex != -1) {
            // This is a cache variable (option)
            val value = if (command.arguments.size > 1) command.arguments[1] else ""
            val type = if (cacheIndex + 1 < command.arguments.size)
                command.arguments[cacheIndex + 1].uppercase() else "STRING"
            val description = if (cacheIndex + 2 < command.arguments.size)
                command.arguments[cacheIndex + 2] else ""

            val optionType = when (type) {
                "BOOL" -> OptionType.BOOL
                "STRING" -> OptionType.STRING
                "PATH" -> OptionType.PATH
                "FILEPATH" -> OptionType.FILEPATH
                "INTERNAL" -> OptionType.INTERNAL
                else -> OptionType.UNKNOWN
            }

            context.addOption(Option(
                name = name,
                description = description,
                defaultValue = value,
                type = optionType
            ))
        } else {
            // Regular variable
            val value = command.arguments.drop(1).joinToString(" ")
            context.setVariable(name, value)
        }
    }

    private fun handleEnableLanguage(command: CommandInvocation, context: AnalysisContext) {
        context.languages.addAll(command.arguments)
    }

    private fun handleInclude(command: CommandInvocation, context: AnalysisContext, currentDir: File) {
        if (command.arguments.isEmpty()) return

        val includePath = command.arguments[0]
        val includeFile = File(currentDir, includePath)

        if (includeFile.exists()) {
            analyzeCMakeFile(includeFile, context)
        }
    }

    private fun handleTargetSources(command: CommandInvocation, context: AnalysisContext) {
        if (command.arguments.isEmpty()) return

        val targetName = command.arguments[0]
        val sources = command.arguments.drop(1).filter {
            !it.uppercase().matches(Regex("PUBLIC|PRIVATE|INTERFACE"))
        }

        context.updateTarget(targetName) { target ->
            target.copy(sources = target.sources + sources)
        }
    }

    private fun handleSetTargetProperties(command: CommandInvocation, context: AnalysisContext) {
        if (command.arguments.isEmpty()) return

        val propertiesIndex = command.arguments.indexOfFirst { it.uppercase() == "PROPERTIES" }
        if (propertiesIndex == -1) return

        val targets = command.arguments.subList(0, propertiesIndex)
        val properties = mutableMapOf<String, String>()

        var i = propertiesIndex + 1
        while (i < command.arguments.size - 1) {
            properties[command.arguments[i]] = command.arguments[i + 1]
            i += 2
        }

        for (targetName in targets) {
            context.updateTarget(targetName) { target ->
                target.copy(properties = target.properties + properties)
            }
        }
    }

    private fun handleAddDependencies(command: CommandInvocation, context: AnalysisContext) {
        if (command.arguments.isEmpty()) return

        val targetName = command.arguments[0]
        val dependencies = command.arguments.drop(1)

        context.updateTarget(targetName) { target ->
            target.copy(dependencies = target.dependencies + dependencies)
        }
    }

    private fun isKeyword(arg: String): Boolean {
        val keywords = setOf(
            "PUBLIC", "PRIVATE", "INTERFACE", "REQUIRED", "OPTIONAL", "EXACT",
            "COMPONENTS", "VERSION", "DESCRIPTION", "LANGUAGES", "CACHE",
            "PROPERTIES", "SYSTEM", "BEFORE", "AFTER"
        )
        return arg.uppercase() in keywords
    }

    private fun buildProject(context: AnalysisContext): CMakeProject {
        return CMakeProject(
            rootDirectory = context.rootDirectory.absolutePath,
            projectName = context.projectName,
            projectVersion = context.projectVersion,
            projectDescription = context.projectDescription,
            languages = context.languages.toList(),
            targets = context.targets.values.toList(),
            dependencies = context.dependencies.toList(),
            options = context.options.toList(),
            variables = context.variables.toMap(),
            subprojects = context.subprojects.toList(),
            cmakeFiles = context.cmakeFiles.toList(),
            cmakeMinimumVersion = context.cmakeMinimumVersion
        )
    }
}

