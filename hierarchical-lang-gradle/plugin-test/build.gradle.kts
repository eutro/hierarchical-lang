plugins {
    id("com.github.eutro.hierarchical-lang") version "1.1.2"
}

apply("groovybuild.groovy")

tasks.register<Copy>("copyTask") {
    destinationDir = file("out")
    from("src/expand_me.json") {
        expandJson(this) {
            setPrettyPrinting()
        }
    }
    from("src/flatten_me.json") {
        flattenJson(this) {
            setPrettyPrinting()
        }
    }
}
