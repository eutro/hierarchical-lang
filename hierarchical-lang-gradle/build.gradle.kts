plugins {
    id("com.gradle.plugin-publish") version "0.15.0"
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation("com.google.code.gson:gson:2.8.7")
    implementation(project(":flattener"))
}

gradlePlugin {
    plugins {
        create("hierarchicalLangPlugin") {
            id = "eutro.hierarchical-lang"
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
