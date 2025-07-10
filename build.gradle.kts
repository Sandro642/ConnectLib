plugins {
    id("java")
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("maven-publish") // Plugin pour la publication
}

group = "fr.sandro642.github"
version = "0.1.3.14-SNAPSHOT" // Version de votre projet

// Ajoutez cette tâche à votre build.gradle.kts
tasks.register("printVersion") {
    doLast {
        println(project.version)
    }
}

repositories {
    mavenCentral()

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("io.projectreactor:reactor-core:3.6.9")
    implementation("org.springframework.boot:spring-boot-starter-webflux:3.2.2")

    implementation("org.openjfx:javafx-controls:21")
    implementation("org.openjfx:javafx-fxml:21")

    compileOnly("org.spigotmc:spigot-api:1.8-R0.1-SNAPSHOT")
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("fr.sandro642.github.ConnectorAPI")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "fr.sandro642.github.ConnectorAPI"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.test {
    useJUnitPlatform()
}

java {
    withSourcesJar() // Ajoute le JAR des sources
    withJavadocJar() // Ajoute le JAR de Javadoc
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = project.group.toString()
            artifactId = "ConnectorAPI"
            version = project.version.toString()
            pom {
                name.set("ConnectorAPI")
                description.set("A library for connecting to APIs")
                url.set("https://sandro642.github.io/connectorapi")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("sandro642")
                        name.set("Sandro")
                        email.set("sandro33810@gmail.com") // Correction de l'email (point-virgule -> point)
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/sandro642/ConnectorAPI.git")
                    developerConnection.set("scm:git:ssh://github.com/sandro642/ConnectorAPI.git")
                    url.set("https://github.com/sandro642/ConnectorAPI")
                }
            }
        }
    }
    repositories {
        maven {
            name = "LocalRepo"
            url = uri(layout.buildDirectory.dir("repo")) // Publie dans build/repo
        }
    }
}