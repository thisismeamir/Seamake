plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.serialization") version "2.2.21"
    id("maven-publish")
}

group = "com.github.thisismeamir.seemake"
version = System.getenv("VERSION") ?: "1.0.3"

allprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    dependencies {
        val implementation by configurations
        val testImplementation by configurations

        implementation(kotlin("stdlib"))
        testImplementation(kotlin("test"))
    }

    kotlin {
        jvmToolchain(21)
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    // Configure source and javadoc jars for publishing
    val sourcesJar by tasks.registering(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    val javadocJar by tasks.registering(Jar::class) {
        archiveClassifier.set("javadoc")
        from(tasks.named("javadoc"))
    }

    afterEvaluate {
        publishing {
            publications {
                create<MavenPublication>("maven") {
                    from(components["java"])
                    artifact(sourcesJar)
                    artifact(javadocJar)

                    pom {
                        name.set(project.name)
                        description.set("Seemake - CMake analyzer for Kotlin/Java")
                        url.set("https://github.com/thisismeamir/Seemake")

                        licenses {
                            license {
                                name.set("The Apache License, Version 2.0")
                                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                            }
                        }

                        developers {
                            developer {
                                id.set("thisismeamir")
                                name.set("Amir")
                                email.set("your-email@example.com")
                            }
                        }

                        scm {
                            connection.set("scm:git:git://github.com/thisismeamir/Seemake.git")
                            developerConnection.set("scm:git:ssh://github.com/thisismeamir/Seemake.git")
                            url.set("https://github.com/thisismeamir/Seemake")
                        }
                    }
                }
            }
        }
    }
}