rootProject.name = "hierarchical-lang"
include("flattener", "hierarchical-lang", "hierarchical-lang-gradle")

pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }
}
