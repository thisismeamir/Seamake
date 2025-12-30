plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    application
}

dependencies {
    implementation(project(":parser"))
    implementation("org.antlr:antlr4-runtime:4.13.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}

application {
    mainClass.set("com.github.thisismeamir.seemake.analyzer.SeemakeKt")
    applicationName = "seemake"

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
            from(rootProject.file("README.md")) {
                into("")
            }
            from(rootProject.file("LICENSE")) {
                into("")
            }
        }
    }
}

tasks.jar {
    dependsOn(":parser:jar")
    manifest {
        attributes["Main-Class"] = "com.github.thisismeamir.seemake.analyzer.SeemakeKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

// Create a standalone executable JAR
tasks.register<Jar>("executableJar") {
    dependsOn(tasks.jar)
    archiveClassifier.set("standalone")
    manifest {
        attributes["Main-Class"] = "com.github.thisismeamir.seemake.analyzer.SeemakeKt"
        attributes["Implementation-Version"] = project.version
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
    with(tasks.jar.get())
}

tasks.named("build") {
    dependsOn("executableJar")
}