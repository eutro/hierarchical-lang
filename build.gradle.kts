plugins {
    java
}

subprojects {
    apply(plugin = "java")

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        withSourcesJar()
    }

    base {
        version = properties["version"] as String
        group = properties["maven_group"] as String
    }
}
