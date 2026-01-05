package com.github.thisismeamir.seemake.analyzer.testutils

import com.github.thisismeamir.seemake.analyzer.model.CMakeProject
import com.github.thisismeamir.seemake.analyzer.model.CMakeTarget
import com.github.thisismeamir.seemake.analyzer.model.TargetType
import java.io.File

// This modules helps us during test to load different sample projects
// located in the <root-directory>/sample-projects with some additional info
// written manually to ensure the tests are working as expected.
interface SampleProject {
    val name: String
    val rootDir: File
    val cmakeProject: CMakeProject
}

object HelloCMakeProject : SampleProject {
    override val name: String
        get() = "hello_cmake"
    override val rootDir: File
        get() = File("../sample-projects/01-basic/A-hello-cmake")
    override val cmakeProject = CMakeProject(
        rootDirectory = "../sample-projects/01-basic/A-hello-cmake",
        projectName = "hello_cmake",
        projectVersion = null,
        projectDescription = null,
        languages = listOf("CXX"),
        cmakeTargets = listOf(
            CMakeTarget(
                name = "hello_cmake",
                type = TargetType.EXECUTABLE,
                sources = listOf("main.cpp"),
                linkLibraries = emptyList(),
                includeDirectories = emptyList(),
                compileDefinitions = emptyList(),
                compileOptions = emptyList(),
                properties = emptyMap(),
                dependencies = emptyList(),
                isImported = false,
                location = null
            )
        ),
        dependencies = emptyList(),
        options = emptyList(),
        variables = emptyMap(),
        subprojects = emptyList(),
        cmakeFiles = listOf("CMakeLists.txt"),
        cmakeMinimumVersion = "3.5"
    )
}


object HelloHeadersProject : SampleProject {
    override val name: String
        get() = "hello_headers"
    override val rootDir: File
        get() = File("../sample-projects/01-basic/B-hello-headers")
    override val cmakeProject = CMakeProject(
        rootDirectory = File("../sample-projects/01-basic/B-hello-headers").absolutePath,
        projectName = "hello_headers",
        projectVersion = null,
        projectDescription = null,
        languages = listOf("C++"),
        cmakeTargets = listOf(
            CMakeTarget(
                name = "hello_headers",
                type = TargetType.EXECUTABLE,
                sources = listOf("src/Hello.cpp", "src/main.cpp"),
                linkLibraries = emptyList(),
                includeDirectories = listOf("${"$"}{PROJECT_SOURCE_DIR}/include"),
                compileDefinitions = emptyList(),
                compileOptions = emptyList(),
                properties = emptyMap(),
                dependencies = emptyList(),
                isImported = false,
                location = null
            )
        ),
        dependencies = emptyList(),
        options = emptyList(),
        variables = mapOf("SOURCES" to "src/Hello.cpp src/main.cpp"),
        subprojects = emptyList(),
        cmakeFiles = listOf("CMakeLists.txt"),
        cmakeMinimumVersion = "3.5"
    )
}

object StaticLibraryProject : SampleProject {
    override val name: String
        get() = "hello_library"
    override val rootDir: File
        get() = File("../sample-projects/01-basic/C-static-library")
    override val cmakeProject = CMakeProject(
        rootDirectory = "../sample-projects/01-basic/C-static-library",
        projectName = "hello_library",
        projectVersion = null,
        projectDescription = null,
        languages = listOf("C++"),
        cmakeTargets = listOf(
            CMakeTarget(
                name = "hello_library",
                type = TargetType.STATIC_LIBRARY,
                sources = listOf("src/Hello.cpp"),
                linkLibraries = emptyList(),
                includeDirectories = listOf("${"$"}{PROJECT_SOURCE_DIR}/include"),
                compileDefinitions = emptyList(),
                compileOptions = emptyList(),
                properties = emptyMap(),
                dependencies = emptyList(),
                isImported = false,
                location = null
            ),
            CMakeTarget(
                name = "hello_binary",
                type = TargetType.EXECUTABLE,
                sources = listOf("src/main.cpp"),
                linkLibraries = listOf("hello_library"),
                includeDirectories = emptyList(),
                compileDefinitions = emptyList(),
                compileOptions = emptyList(),
                properties = emptyMap(),
                dependencies = emptyList(),
                isImported = false,
                location = null
            )
        ),
        dependencies = emptyList(),
        options = emptyList(),
        variables = emptyMap(),
        subprojects = emptyList(),
        cmakeFiles = listOf("CMakeLists.txt"),
        cmakeMinimumVersion = "3.5"
    )
}


object SharedLibraryProject : SampleProject {
    override val name: String
        get() = "hello_library"
    override val rootDir: File
        get() = File("../sample-projects/01-basic/D-shared-library")
    override val cmakeProject = CMakeProject(
        rootDirectory = "../sample-projects/01-basic/D-shared-library",
        projectName = "hello_library",
        projectVersion = null,
        projectDescription = null,
        languages = listOf("C++"),
        cmakeTargets = listOf(
            CMakeTarget(
                name = "hello_library",
                type = TargetType.SHARED_LIBRARY,
                sources = listOf("src/Hello.cpp"),
                linkLibraries = emptyList(),
                includeDirectories = listOf("${"$"}{PROJECT_SOURCE_DIR}/include"),
                compileDefinitions = emptyList(),
                compileOptions = emptyList(),
                properties = emptyMap(),
                dependencies = emptyList(),
                isImported = false,
                location = null
            ),
            CMakeTarget(
                name = "hello_binary",
                type = TargetType.EXECUTABLE,
                sources = listOf("src/main.cpp"),
                linkLibraries = listOf("hello::library"),
                includeDirectories = emptyList(),
                compileDefinitions = emptyList(),
                compileOptions = emptyList(),
                properties = emptyMap(),
                dependencies = emptyList(),
                isImported = false,
                location = null
            )
        ),
        dependencies = emptyList(),
        options = emptyList(),
        variables = emptyMap(),
        subprojects = emptyList(),
        cmakeFiles = listOf("CMakeLists.txt"),
        cmakeMinimumVersion = "3.5"
    )
}

object InstallingProject : SampleProject {
    override val name: String
        get() = "cmake_examples_install"
    override val rootDir: File
        get() = File("../sample-projects/01-basic/E-installing")
    override val cmakeProject = CMakeProject(
        rootDirectory = "../sample-projects/01-basic/E-installing",
        projectName = "cmake_examples_install",
        projectVersion = null,
        projectDescription = null,
        languages = listOf("C++"),
        cmakeTargets = listOf(
            CMakeTarget(
                name = "cmake_examples_inst",
                type = TargetType.SHARED_LIBRARY,
                sources = listOf("src/Hello.cpp"),
                linkLibraries = emptyList(),
                includeDirectories = listOf("${"$"}{PROJECT_SOURCE_DIR}/include"),
                compileDefinitions = emptyList(),
                compileOptions = emptyList(),
                properties = emptyMap(),
                dependencies = emptyList(),
                isImported = false,
                location = null
            ),
            CMakeTarget(
                name = "cmake_examples_inst_bin",
                type = TargetType.EXECUTABLE,
                sources = listOf("src/main.cpp"),
                linkLibraries = listOf("cmake_examples_inst"),
                includeDirectories = emptyList(),
                compileDefinitions = emptyList(),
                compileOptions = emptyList(),
                properties = emptyMap(),
                dependencies = emptyList(),
                isImported = false,
                location = null
            )
        ),
        dependencies = emptyList(),
        options = emptyList(),
        variables = emptyMap(),
        subprojects = emptyList(),
        cmakeFiles = listOf("CMakeLists.txt"),
        cmakeMinimumVersion = "3.5"
    )
}

object BuildTypeProject : SampleProject {
    override val name: String
        get() = "build_type"
    override val rootDir: File
        get() = File("../sample-projects/01-basic/F-build-type")
    override val cmakeProject = CMakeProject(
        rootDirectory = "../sample-projects/01-basic/F-build-type",
        projectName = "build_type",
        projectVersion = null,
        projectDescription = null,
        languages = listOf("C++"),
        cmakeTargets = listOf(
            CMakeTarget(
                name = "cmake_examples_build_type",
                type = TargetType.EXECUTABLE,
                sources = listOf("main.cpp"),
                linkLibraries = emptyList(),
                includeDirectories = emptyList(),
                compileDefinitions = emptyList(),
                compileOptions = emptyList(),
                properties = emptyMap(),
                dependencies = emptyList(),
                isImported = false,
                location = null
            )
        ),
        dependencies = emptyList(),
        options = emptyList(),
        variables = mapOf("CMAKE_BUILD_TYPE" to "RelWithDebInfo"),
        subprojects = emptyList(),
        cmakeFiles = listOf("CMakeLists.txt"),
        cmakeMinimumVersion = "3.5"
    )
}

object CompileFlagsProject : SampleProject {
    override val name: String
        get() = "compile_flags"
    override val rootDir: File
        get() = File("../sample-projects/01-basic/G-compile-flags")
    override val cmakeProject = CMakeProject(
        rootDirectory = "../sample-projects/01-basic/G-compile-flags",
        projectName = "compile_flags",
        projectVersion = null,
        projectDescription = null,
        languages = listOf("C++"),
        cmakeTargets = listOf(
            CMakeTarget(
                name = "cmake_examples_compile_flags",
                type = TargetType.EXECUTABLE,
                sources = listOf("main.cpp"),
                linkLibraries = emptyList(),
                includeDirectories = emptyList(),
                compileDefinitions = listOf("EX3"),
                compileOptions = listOf("-DEX2"),
                properties = emptyMap(),
                dependencies = emptyList(),
                isImported = false,
                location = null
            )
        ),
        dependencies = emptyList(),
        options = emptyList(),
        variables = emptyMap(),
        subprojects = emptyList(),
        cmakeFiles = listOf("CMakeLists.txt"),
        cmakeMinimumVersion = "3.5"
    )
}
object ThirdPartyIncludeProject : SampleProject {
    override val name: String
        get() = "third_party_include"
    override val rootDir: File
        get() = File("../sample-projects/01-basic/H-third-party-library")
    override val cmakeProject = CMakeProject(
        rootDirectory = "../sample-projects/01-basic/H-third-party-library",
        projectName = "third_party_include",
        projectVersion = null,
        projectDescription = null,
        languages = listOf("C++"),
        cmakeTargets = listOf(
            CMakeTarget(
                name = "third_party_include",
                type = TargetType.EXECUTABLE,
                sources = listOf("main.cpp"),
                linkLibraries = listOf("Boost::filesystem"),
                includeDirectories = emptyList(),
                compileDefinitions = emptyList(),
                compileOptions = emptyList(),
                properties = emptyMap(),
                dependencies = listOf("Boost"),
                isImported = false,
                location = null
            )
        ),
        dependencies = emptyList(),
        options = emptyList(),
        variables = mapOf("Boost_FOUND" to "true"),
        subprojects = emptyList(),
        cmakeFiles = listOf("CMakeLists.txt"),
        cmakeMinimumVersion = "3.5"
    )
}
object ImportedTargetsProject : SampleProject {
    override val name: String
        get() = "imported_targets"
    override val rootDir: File
        get() = File("../sample-projects/01-basic/K-imported-targets")
    override val cmakeProject = CMakeProject(
        rootDirectory = "../sample-projects/01-basic/K-imported-targets",
        projectName = "imported_targets",
        projectVersion = null,
        projectDescription = null,
        languages = listOf("C++"),
        cmakeTargets = listOf(
            CMakeTarget(
                name = "imported_targets",
                type = TargetType.EXECUTABLE,
                sources = listOf("main.cpp"),
                linkLibraries = listOf("Boost::filesystem"),
                includeDirectories = emptyList(),
                compileDefinitions = emptyList(),
                compileOptions = emptyList(),
                properties = emptyMap(),
                dependencies = listOf("Boost"),
                isImported = false,
                location = null
            )
        ),
        dependencies = emptyList(),
        options = emptyList(),
        variables = mapOf("Boost_FOUND" to "true"),
        subprojects = emptyList(),
        cmakeFiles = listOf("CMakeLists.txt"),
        cmakeMinimumVersion = "3.5"
    )
}

object CppStandardCommonMethodProject : SampleProject {
    override val name: String
        get() = "hello_cpp11"
    override val rootDir: File
        get() = File("../sample-projects/01-basic/L-cpp-standard/i-common-method")
    override val cmakeProject = CMakeProject(
        rootDirectory = "../sample-projects/01-basic/L-cpp-standard/i-common-method",
        projectName = "hello_cpp11",
        projectVersion = null,
        projectDescription = null,
        languages = listOf("C++"),
        cmakeTargets = listOf(
            CMakeTarget(
                name = "hello_cpp11",
                type = TargetType.EXECUTABLE,
                sources = listOf("main.cpp"),
                linkLibraries = emptyList(),
                includeDirectories = emptyList(),
                compileDefinitions = emptyList(),
                compileOptions = listOf("-std=c++11"),
                properties = emptyMap(),
                dependencies = emptyList(),
                isImported = false,
                location = null
            )
        ),
        dependencies = emptyList(),
        options = emptyList(),
        variables = mapOf("CMAKE_CXX_STANDARD" to "11"),
        subprojects = emptyList(),
        cmakeFiles = listOf("CMakeLists.txt"),
        cmakeMinimumVersion = "3.1"
    )
}

object CxxStandardMethodProject : SampleProject {
    override val name: String
        get() = "hello_cpp11"
    override val rootDir: File
        get() = File("../sample-projects/01-basic/L-cpp-standard/ii-cxx-standard")
    override val cmakeProject = CMakeProject(
        rootDirectory = "../sample-projects/01-basic/L-cpp-standard/ii-cxx-standard",
        projectName = "hello_cpp11",
        projectVersion = null,
        projectDescription = null,
        languages = listOf("C++"),
        cmakeTargets = listOf(
            CMakeTarget(
                name = "hello_cpp11",
                type = TargetType.EXECUTABLE,
                sources = listOf("main.cpp"),
                linkLibraries = emptyList(),
                includeDirectories = emptyList(),
                compileDefinitions = emptyList(),
                compileOptions = emptyList(),
                properties = emptyMap(),
                dependencies = emptyList(),
                isImported = false,
                location = null
            )
        ),
        dependencies = emptyList(),
        options = emptyList(),
        variables = emptyMap(),
        subprojects = emptyList(),
        cmakeFiles = listOf("CMakeLists.txt"),
        cmakeMinimumVersion = "3.1"
    )
}


object CompileFeaturesMethodProject : SampleProject {
    override val name: String
        get() = "hello_cpp11"
    override val rootDir: File
        get() = File("../sample-projects/01-basic/L-cpp-standard/iii-compile-features")
    override val cmakeProject = CMakeProject(
        rootDirectory = "../sample-projects/01-basic/L-cpp-standard/iii-compile-features",
        projectName = "hello_cpp11",
        projectVersion = null,
        projectDescription = null,
        languages = listOf("C++"),
        cmakeTargets = listOf(
            CMakeTarget(
                name = "hello_cpp11",
                type = TargetType.EXECUTABLE,
                sources = listOf("main.cpp"),
                linkLibraries = emptyList(),
                includeDirectories = emptyList(),
                compileDefinitions = emptyList(),
                compileOptions = emptyList(),
                properties = emptyMap(),
                dependencies = emptyList(),
                isImported = false,
                location = null
            )
        ),
        dependencies = emptyList(),
        options = emptyList(),
        variables = emptyMap(),
        subprojects = emptyList(),
        cmakeFiles = listOf("CMakeLists.txt"),
        cmakeMinimumVersion = "3.1"
    )
}
