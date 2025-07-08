plugins {
    id("java")
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("maven-publish") // Plugin pour la publication
}

group = "fr.sandro642.github"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("io.projectreactor:reactor-core:3.6.9")
    implementation("org.springframework.boot:spring-boot-starter-webflux:3.2.2")

    implementation("org.openjfx:javafx-controls:21")
    implementation("org.openjfx:javafx-fxml:21")
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
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/sandro642/ConnectorAPI") // Remplacez par votre OWNER/REPO
            credentials {
                username = System.getenv("USERNAME")
                password = System.getenv("TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = project.group.toString()
            artifactId = "ConnectorAPI"
            version = project.version.toString()
        }
    }
}