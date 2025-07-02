plugins {
    id("java")
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
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

    // JavaFX (nécessaire si jamais le plugin ne les ajoute pas automatiquement)
    implementation("org.openjfx:javafx-controls:21")
    implementation("org.openjfx:javafx-fxml:21")
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("fr.sandro642.github.ConnectorAPI") // ta classe principale JavaFX
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "fr.sandro642.github.ConnectorAPI"
    }
    // Inclure les dépendances dans le JAR (optionnel si tu veux un "fat JAR")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

tasks.test {
    useJUnitPlatform()
}