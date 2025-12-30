import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    antlr
}

dependencies {
    // ANTLR dependencies
    antlr("org.antlr:antlr4:4.13.1")
    implementation("org.antlr:antlr4-runtime:4.13.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}

// =============================================================================
// ANTLR CONFIGURATION
// =============================================================================

tasks.generateGrammarSource {
    maxHeapSize = "64m"

    arguments = arguments + listOf(
        "-visitor",
        "-listener",
        "-package", "com.github.thisismeamir.seemake.parser",
        "-long-messages"
    )

    outputDirectory = file("src/main/generated/")
}

// Task to move generated files to the correct package directory
abstract class MoveGeneratedFilesTask : DefaultTask() {
    @get:InputDirectory
    abstract val sourceDir: DirectoryProperty

    @get:OutputDirectory
    abstract val targetDir: DirectoryProperty

    @TaskAction
    fun moveFiles() {
        val source = sourceDir.get().asFile
        val target = targetDir.get().asFile

        target.mkdirs()

        source.listFiles()?.filter { it.isFile }?.forEach { file ->
            file.copyTo(target.resolve(file.name), overwrite = true)
            file.delete()
        }
    }
}

val moveGeneratedFiles = tasks.register<MoveGeneratedFilesTask>("moveGeneratedFiles") {
    dependsOn(tasks.generateGrammarSource)
    sourceDir.set(layout.projectDirectory.dir("src/main/generated"))
    targetDir.set(layout.projectDirectory.dir("src/main/generated/com/github/thisismeamir/seemake/parser"))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn(moveGeneratedFiles)
    dependsOn(tasks.generateTestGrammarSource)
}

sourceSets {
    main {
        java {
            srcDir("src/main/generated/")
        }
    }
}