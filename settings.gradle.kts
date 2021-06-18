rootProject.name = "hierarchical-lang"
include("flattener", "hierarchical-lang")

pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }
}
