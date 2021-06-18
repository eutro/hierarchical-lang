plugins {
    java
    `maven-publish`
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
}

base {
    version = "1.0.0"
    group = "eutro"
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
