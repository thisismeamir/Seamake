plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.serialization")  version "2.2.21"
    application
    antlr
}

group = "io.github.thisismeamir.seamake"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    // ANTLR dependencies
    antlr("org.antlr:antlr4:4.+")
    implementation("org.antlr:antlr4-runtime:4.13.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    // Kotlin dependencies
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}

// =============================================================================
// ANTLR CONFIGURATION
// =============================================================================

tasks.generateGrammarSource {
    maxHeapSize = "64m"

    arguments = arguments + listOf(
        "-visitor",
        "-listener",
        "-package", "com.iskportal.koly.parsers",
        "-long-messages"
    )

    outputDirectory = file("src/main/generated/io/github/thisismeamir/parsers") 
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn(tasks.generateGrammarSource)
    dependsOn(tasks.generateTestGrammarSource)
}

sourceSets {
    main {
        java {
            srcDir("src/main/generated/")
        }
    }
}

// =============================================================================
// APPLICATION CONFIGURATION
// =============================================================================

application {
    mainClass.set("io.github.thisismeamir.seamake.analyzers.parser.app.MainKt")
    applicationName = "seamake"

    applicationDefaultJvmArgs = listOf(
        "-Xmx512m",
        "-Xms256m",
        "-XX:+UseG1GC",
        "-Dfile.encoding=UTF-8",
        "-Duser.timezone=UTC"
    )
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

// =============================================================================
// DISTRIBUTION CONFIGURATION
// =============================================================================

distributions {
    main {
        contents {
            from("README.md")
            from("LICENSE")
        }
    }
}

// =============================================================================
// KOTLIN & JAVA TOOLCHAIN CONFIGURATION
// =============================================================================

kotlin {
    jvmToolchain(17)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
