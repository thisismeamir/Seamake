# Seamake - CMake Project Analyzer

Seamake is a comprehensive CMake analyzer and helper tool designed for development of build automation scripts, particularly for HepSW and Unipac package managers. It provides deep analysis of CMake projects, extracting information about targets, dependencies, options, and build configurations.

## Features

- **Complete Project Analysis**: Analyzes entire CMake projects including subdirectories
- **Dependency Detection**: Identifies all dependencies with versions, components, and constraints
- **Target Information**: Extracts all build targets (executables, libraries) with their properties
- **Build Options**: Discovers all configurable options and their defaults
- **Extensible Architecture**: Support for custom edge case handlers
- **Multiple Report Formats**: Text, JSON, and Graphviz dependency graphs
- **Recursive Analysis**: Handles complex projects with multiple subdirectories

## Architecture

### Core Components

1. **CMakeFileParser** - Parses CMakeLists.txt files using ANTLR-generated parser
2. **CMakeAnalyzer** - Main analysis engine that processes commands and builds the project model
3. **EdgeCaseHandler** - Interface for handling special CMake patterns
4. **ReportGenerator** - Generates various report formats

### Data Model

- **CMakeProject**: Complete project representation
- **Target**: Build targets (executables, libraries)
- **Dependency**: External dependencies (find_package, FetchContent, etc.)
- **Option**: Build configuration options
- **VersionConstraint**: Dependency version requirements

## Setup

### Prerequisites

1. **Kotlin/Java**: JDK 17 or higher
2. **ANTLR Parser**: Generate parser from CMake.g4 grammar

### Generate Parser

You mentioned you already have the Java parser generated from CMake.g4. Place these files in your project:

```
src/main/java/com/seamake/analyzer/parser/
├── CMakeLexer.java
├── CMakeParser.java
├── CMakeBaseListener.java
└── CMakeListener.java
```

If you need to regenerate:

```bash
# Download ANTLR
wget https://www.antlr.org/download/antlr-4.13.1-complete.jar

# Generate parser
java -jar antlr-4.13.1-complete.jar \
    -package com.seamake.analyzer.parser \
    -o src/main/java/com/seamake/analyzer/parser \
    CMake.g4
```

### Build

```bash
# Build with Gradle
./gradlew build

# Create executable JAR
./gradlew jar
```

## Usage

### Command Line

```bash
# Basic usage
kotlin -cp build/libs/seamake-1.0.0.jar com.seamake.analyzer.SeamakeKt /path/to/project

# Specify output directory
kotlin -cp build/libs/seamake-1.0.0.jar com.seamake.analyzer.SeamakeKt /path/to/project ./reports
```

### Programmatic Usage

#### Basic Analysis

```kotlin
import com.seamake.analyzer.Seamake
import java.io.File

val projectDir = File("/path/to/cmake/project")
val project = Seamake.analyze(projectDir)

println("Project: ${project.projectName}")
println("Targets: ${project.targets.size}")
println("Dependencies: ${project.dependencies.size}")
```

#### Generate Reports

```kotlin
import com.seamake.analyzer.Seamake
import java.io.File

val projectDir = File("/path/to/cmake/project")
val outputDir = File("./output")

Seamake.analyzeAndSaveReports(projectDir, outputDir)
```

#### Access Specific Information

```kotlin
val project = Seamake.analyze(projectDir)

// List all executables
project.targets
    .filter { it.type == TargetType.EXECUTABLE }
    .forEach { println("Executable: ${it.name}") }

// List dependencies with versions
project.dependencies
    .filter { it.version != null }
    .forEach { println("${it.name} ${it.version}") }

// List build options
project.options.forEach { option ->
    println("${option.name}: ${option.defaultValue}")
}
```

## Edge Case Handlers

Seamake supports custom handlers for special CMake patterns. This is useful for:
- Custom macros/functions
- Company-specific CMake modules
- Non-standard dependency management
- Generator expressions
- Variable references

### Built-in Handlers

1. **CustomMacroHandler**: Handles custom library/executable macros
2. **GeneratorExpressionHandler**: Processes generator expressions like `$<CONFIG:Debug>`
3. **VariableReferenceHandler**: Resolves variable references like `${VAR_NAME}`
4. **PkgConfigHandler**: Handles pkg-config dependencies
5. **ConditionalBlockHandler**: Processes if/elseif/else/endif blocks

### Creating Custom Handlers

```kotlin
class MyCustomHandler : EdgeCaseHandler {
    override fun canHandle(command: CommandInvocation): Boolean {
        return command.name == "my_custom_function"
    }
    
    override fun handle(
        command: CommandInvocation, 
        context: AnalysisContext, 
        currentDir: File
    ) {
        // Process the command
        val name = command.arguments[0]
        // ... extract and process arguments
        
        // Update context
        context.addTarget(/* ... */)
        context.addDependency(/* ... */)
    }
    
    override fun priority(): Int = 10
}
```

### Using Custom Handlers

```kotlin
val handlers = listOf(
    MyCustomHandler(),
    CustomMacroHandler(),
    VariableReferenceHandler()
)

val project = Seamake.analyze(projectDir, handlers)
```

## Report Formats

### Text Report

Comprehensive human-readable report with all project information:

```
================================================================================
CMake Project Analysis Report
================================================================================

PROJECT INFORMATION
--------------------------------------------------------------------------------
Name: MyProject
Version: 1.0.0
Description: A sample project
...

TARGETS (5)
--------------------------------------------------------------------------------

Target: myapp
  Type: EXECUTABLE
  Sources: 3 file(s)
    - src/main.cpp
    - src/app.cpp
  Link Libraries:
    - mylib
    - Boost::system
...
```

### JSON Report

Machine-readable format for integration with other tools:

```json
{
  "projectName": "MyProject",
  "projectVersion": "1.0.0",
  "targets": [
    {
      "name": "myapp",
      "type": "EXECUTABLE",
      "sources": ["src/main.cpp", "src/app.cpp"],
      "linkLibraries": ["mylib", "Boost::system"]
    }
  ],
  "dependencies": [...]
}
```

### Dependency Graph

Graphviz DOT format for visualization:

```bash
# Generate graph
dot -Tpng dependencies.dot -o dependencies.png

# Or use any Graphviz viewer
xdot dependencies.dot
```

### Summary Report

Quick overview with statistics:

```
CMake Project Summary
==================================================
Project: MyProject
Version: 1.0.0

Statistics:
  - Targets: 5
    - Executables: 2
    - Libraries: 3
  - Dependencies: 8
    - find_package: 5
    - FetchContent: 2
    - Subdirectories: 1
  - Options: 4
```

## Supported CMake Commands

Seamake recognizes and processes the following CMake commands:

### Project Configuration
- `cmake_minimum_required()`
- `project()`
- `enable_language()`

### Targets
- `add_executable()`
- `add_library()` (STATIC, SHARED, MODULE, OBJECT, INTERFACE)
- `target_link_libraries()`
- `target_include_directories()`
- `target_compile_definitions()`
- `target_compile_options()`
- `target_sources()`
- `set_target_properties()`
- `add_dependencies()`

### Dependencies
- `find_package()`
- `fetchcontent_declare()`
- `add_subdirectory()`
- `pkg_check_modules()` (via edge case handler)

### Variables & Options
- `option()`
- `set()` (including CACHE variables)

### Control Flow
- `if()`, `elseif()`, `else()`, `endif()` (via edge case handler)

### Other
- `include()`

## Advanced Features

### Variable Resolution

The analyzer resolves CMake variables:

```cmake
set(MY_SOURCES file1.cpp file2.cpp)
add_executable(myapp ${MY_SOURCES})
```

Result: `myapp` target will have `file1.cpp` and `file2.cpp` as sources

### Generator Expressions

Generator expressions are evaluated (with defaults):

```cmake
target_compile_definitions(myapp PRIVATE 
    $<$<CONFIG:Debug>:DEBUG_MODE>
)
```

### Subdirectory Analysis

Automatically analyzes all subdirectories:

```cmake
add_subdirectory(src)
add_subdirectory(tests)
add_subdirectory(examples)
```

All targets and dependencies from subdirectories are included in the analysis.

### FetchContent Dependencies

Extracts Git repository information:

```cmake
FetchContent_Declare(
    googletest
    GIT_REPOSITORY https://github.com/google/googletest.git
    GIT_TAG release-1.12.1
)
```

Result: Dependency with Git repository and tag information

## Data Model Details

### Target Properties

Each target includes:
- **name**: Target name
- **type**: EXECUTABLE, STATIC_LIBRARY, SHARED_LIBRARY, etc.
- **sources**: Source files
- **linkLibraries**: Libraries to link against
- **includeDirectories**: Include paths
- **compileDefinitions**: Preprocessor definitions
- **compileOptions**: Compiler flags
- **properties**: Custom properties
- **dependencies**: Other targets this depends on

### Dependency Information

Each dependency includes:
- **name**: Dependency name
- **type**: FIND_PACKAGE, FETCH_CONTENT, SUBDIRECTORY, etc.
- **version**: Required version (if specified)
- **versionConstraint**: Version constraint (EXACT, GREATER, etc.)
- **components**: Required components
- **isRequired**: Whether dependency is required
- **targets**: Imported targets provided
- **gitRepository**: Git URL (for FetchContent)
- **gitTag**: Git tag/branch (for FetchContent)

### Option Configuration

Each option includes:
- **name**: Option name
- **description**: Help text
- **defaultValue**: Default value
- **type**: BOOL, STRING, PATH, FILEPATH, etc.
- **possibleValues**: Valid values (for enums)

## Limitations and Edge Cases

### Known Limitations

1. **Macro/Function Definitions**: Custom macros and functions are not fully expanded
2. **Complex Generator Expressions**: Advanced generator expressions use default evaluations
3. **Conditional Logic**: Some complex conditional logic may not be fully evaluated
4. **External Modules**: Custom CMake modules must be handled with edge case handlers

### Handling Edge Cases

For unsupported patterns, implement custom edge case handlers:

1. Identify the pattern in your CMakeLists.txt
2. Create a handler implementing `EdgeCaseHandler`
3. Register it when creating the analyzer
4. Test with your specific project

Example patterns that need custom handlers:
- Company-specific macros
- Custom find modules
- Non-standard dependency management
- Proprietary build systems

## Integration with Package Managers

### HepSW Integration

```kotlin
// Analyze project
val project = Seamake.analyze(projectDir)

// Extract dependencies for HepSW
val hepswDeps = project.dependencies
    .filter { it.type == DependencyType.FIND_PACKAGE }
    .map { dep ->
        HepSWDependency(
            name = dep.name,
            version = dep.version ?: "latest",
            components = dep.components
        )
    }
```

### Unipac Integration

```kotlin
// Generate Unipac manifest
val manifest = UnipacManifest(
    name = project.projectName ?: "unknown",
    version = project.projectVersion ?: "0.0.0",
    dependencies = project.dependencies.map { 
        UnipacDep(it.name, it.version)
    },
    targets = project.targets.map { it.name }
)
```

## Testing

### Create Sample Project

```kotlin
import com.seamake.analyzer.examples.TestHelper
import java.io.File

val testDir = File("./test_project")
TestHelper.createSampleProject(testDir)

// Analyze the sample project
val project = Seamake.analyze(testDir)
```

### Running Tests

```bash
./gradlew test
```

## Troubleshooting

### Parser Errors

If you encounter parsing errors:
1. Check that CMakeLists.txt is valid CMake syntax
2. Look for unsupported or non-standard commands
3. Create an edge case handler for custom patterns

### Missing Dependencies

If dependencies are not detected:
1. Check if they use standard CMake commands
2. Verify variable references are resolved
3. Add custom handler for proprietary formats

### Incomplete Target Information

If target information is incomplete:
1. Check if targets are defined conditionally
2. Verify include files are processed
3. Enable conditional block handler

## Contributing

To add support for new CMake patterns:

1. Implement `EdgeCaseHandler`
2. Add tests
3. Document the handler
4. Submit with examples

## Performance

- **Small projects** (<10 files): ~100ms
- **Medium projects** (10-100 files): ~500ms
- **Large projects** (>100 files): ~2-5s

Performance is primarily dependent on:
- Number of CMakeLists.txt files
- Depth of subdirectory structure
- Complexity of CMake logic

## Future Enhancements

Potential improvements:
- Full generator expression evaluation
- Macro/function expansion
- Cross-compilation support
- Cache variable tracking
- Target property inheritance
- Install rules analysis
- CTest configuration extraction
- CPack configuration extraction

## License

[Your license here]

## Support

For issues, questions, or contributions:
- GitHub: [your-repo]
- Email: [your-email]
- Documentation: [your-docs]