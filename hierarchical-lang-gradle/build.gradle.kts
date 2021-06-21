plugins {
    id("com.gradle.plugin-publish") version "0.15.0"
    `java-gradle-plugin`
    `maven-publish`
}

base {
    version = "1.1.3"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.7")
    compileOnly(project(":flattener"))
}

tasks.jar {
    from(project(":flattener").tasks.compileJava.get().outputs)
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
