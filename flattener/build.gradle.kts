import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.7")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    register("cli") {
        compileClasspath += main.get().compileClasspath
        runtimeClasspath += main.get().compileClasspath
        compileClasspath += main.get().output
        runtimeClasspath += main.get().output
    }
}

tasks.register<ShadowJar>("flattenerJar") {
    archiveClassifier.set("flattener-cli")
    from(sourceSets.main.get().output)
    from(sourceSets["cli"].output)
    configurations = mutableListOf(project.configurations.compileClasspath.get())
    manifest {
        attributes("Main-Class" to "eutro.jsonflattener.cli.FlattenerMain")
    }
}

tasks.register<ShadowJar>("expanderJar") {
    archiveClassifier.set("expander-cli")
    from(sourceSets.main.get().output)
    from(sourceSets["cli"].output)
    configurations = mutableListOf(project.configurations.compileClasspath.get())
    manifest {
        attributes("Main-Class" to "eutro.jsonflattener.cli.ExpanderMain")
    }
}

tasks.build {
    dependsOn("flattenerJar")
    dependsOn("expanderJar")
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifact(tasks.jar) {
                builtBy(tasks.jar)
            }
            artifact(tasks["sourcesJar"]) {
                builtBy(tasks["sourcesJar"])
            }
        }
    }

    repositories {
    }
}
