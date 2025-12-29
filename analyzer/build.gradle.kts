plugins {
    kotlin("jvm") version "2.2.21"
    application
}

group = "io.github.thisismeamir.seamake.analyzer"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":parser"))
    implementation("org.antlr:antlr4-runtime:4.13.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    // Kotlin dependencies
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("io.github.thisismeamir.seamake.analyzer.SeamakeKt")
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

distributions {
    main {
        contents {
            from("README.md")
            from("LICENSE")
        }
    }
}

kotlin {
    jvmToolchain(21)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "io.github.thisismeamir.seamake.analyzer.SeamakeKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}