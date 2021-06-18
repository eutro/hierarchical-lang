plugins {
    id("fabric-loom") version "0.9-SNAPSHOT"
    `maven-publish`
}

base {
    archivesBaseName = properties["archives_base_name"] as String
}

dependencies {
    minecraft("com.mojang:minecraft:${properties["minecraft_version"]}")
    mappings("net.fabricmc:yarn:${properties["yarn_mappings"]}:v2")
    modImplementation("net.fabricmc:fabric-loader:${properties["loader_version"]}")
    implementation(project(":flattener"))
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.jar {
    from("${rootProject.projectDir}/LICENSE") {
        rename { "${it}_${base.archivesBaseName}" }
    }
    from(project(":flattener").tasks.compileJava.get().outputs)
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifact(tasks.remapJar) {
                builtBy(tasks.remapJar)
            }
            artifact(tasks["sourcesJar"]) {
                builtBy(tasks["remapSourcesJar"])
            }
        }
    }

    repositories {
    }
}
