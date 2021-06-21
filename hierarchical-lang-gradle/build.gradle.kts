import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation
import groovy.namespace.QName
import groovy.util.Node

plugins {
    id("com.gradle.plugin-publish") version "0.15.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    `java-gradle-plugin`
    `maven-publish`
}

base {
    version = "1.1.2"
}

repositories {
    mavenCentral()
}

dependencies {
    shadow(gradleApi())
    shadow(localGroovy())

    implementation("com.google.code.gson:gson:2.8.7")
    implementation(project(":flattener"))
}

tasks.register<ConfigureShadowRelocation>("relocateShadowJar") {
    prefix = "eutro.hierarchicallang.shadow"
    target = tasks.shadowJar.get()
}

tasks.jar {
    enabled = false
}

tasks.shadowJar {
    dependsOn("relocateShadowJar")
    archiveClassifier.set("")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

// needed to prevent inclusion of gradle-api into shadow JAR
configurations {
    implementation {
        dependencies {
            project.dependencies.gradleApi()
        }
    }
}

gradlePlugin {
    plugins {
        create("hierarchicalLangPlugin") {
            id = "com.github.eutro.hierarchical-lang"
            implementationClass = "eutro.hierarchicallang.HierarchicalLangPlugin"
        }
    }
}

pluginBundle {
    vcsUrl = "https://github.com/eutro/hierarchical-lang"
    website = vcsUrl
    description = "Flatten or expand Json files."
    (plugins) {
        "hierarchicalLangPlugin" {
            displayName = "Hierarchical Lang plugin"
        }
    }
    tags = listOf("json", "transform")
}

// Workaround for configuring the artifact that publish-plugins uses: https://github.com/JLLeitschuh/ktlint-gradle/blob/master/plugin/build.gradle.kts
// Need to move publishing configuration into afterEvaluate {}
// to override changes done by "com.gradle.plugin-publish" plugin in afterEvaluate {} block
// See PublishPlugin class for details
afterEvaluate {
    publishing {
        publications {
            withType<MavenPublication> {
                // Special workaround to publish shadow jar instead of normal one. Name to override peeked here:
                // https://github.com/gradle/gradle/blob/master/subprojects/plugin-development/src/main/java/org/gradle/plugin/devel/plugins/MavenPluginPublishPlugin.java#L73
                if (name == "pluginMaven") {
                    setArtifacts(
                        listOf(
                            tasks.shadowJar,
                            tasks.sourcesJar,
                            tasks["publishPluginJavaDocsJar"]
                        )
                    )
                }
                pom {
                    withXml {
                        asNode().run {
                            for (child in children()) {
                                if (child is Node && child.name().let {
                                        it is QName && it.localPart == "dependencies"
                                    }) {
                                    child.children().clear()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
