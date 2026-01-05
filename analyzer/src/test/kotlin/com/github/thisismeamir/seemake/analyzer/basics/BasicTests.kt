package com.github.thisismeamir.seemake.analyzer.basics

import com.github.thisismeamir.seemake.analyzer.core.CMakeAnalyzer
import com.github.thisismeamir.seemake.analyzer.testutils.*
import com.github.thisismeamir.seemake.analyzer.utils.CMakeUtils
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BasicTests {
    // A list of projects that are being used to make sure different
    // scenarios are covered.
    val testingProjects = listOf<SampleProject>(
        HelloCMakeProject,
        HelloHeadersProject,
        StaticLibraryProject,
        SharedLibraryProject,
        InstallingProject,
        BuildTypeProject,
        CompileFlagsProject,
        ThirdPartyIncludeProject,
        ImportedTargetsProject,
        CppStandardCommonMethodProject,
        CxxStandardMethodProject,
        CompileFeaturesMethodProject
    )

    /**
     * A simple test to check where the code is running from.
     */
    @Test
    fun `Checking where the code is running from`() {
        val currentDir = File(".").canonicalFile
        println("Current Directory: $currentDir")
        assertTrue { currentDir.exists() }
    }

    /**
     * A test to find CMakeLists.txt files in the test projects.
     */
    @Test
    fun `CMake File Finder`() {
        testingProjects.forEach { project ->
//            println("CMakeProject: $project")
            val cmakeLists = CMakeUtils.findCMakeFiles(project.rootDir)
//            println("CMakeLists.txt files found: $cmakeLists")
            assertTrue { cmakeLists.isNotEmpty() }
        }
    }

    /**
     * Checking analyzer against Hello CMake Project.
     */
    @Test
    fun `Hello CMake Project`() {
        val analyzedProject = CMakeAnalyzer().analyze(HelloCMakeProject.rootDir)
        assertTrue {
            analyzedProject == HelloCMakeProject.cmakeProject
        }
    }

    /**
     * Checking analyzer against Hello Headers Project.
     */
    @Test
    fun `Hello Headers Project`() {
        val analyzedProject = CMakeAnalyzer().analyze(HelloHeadersProject.rootDir)
        println(analyzedProject)
    }

    /**
     * Checking analyzer against Static Library Project.
     */
    @Test
    fun `Static Library Project`() {
        val analyzedProject = CMakeAnalyzer().analyze(StaticLibraryProject.rootDir)
        assertEquals(
            expected = StaticLibraryProject.cmakeProject,
            actual = analyzedProject
        )
    }

    /**
     * Checking analyzer against Shared Library Project.
     */
    fun `Shared Library Project`() {
        val analyzedProject = CMakeAnalyzer().analyze(SharedLibraryProject.rootDir)
        assertEquals(
            expected = SharedLibraryProject.cmakeProject,
            actual = analyzedProject
        )
    }


}