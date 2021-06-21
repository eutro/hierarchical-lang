plugins {
    id("com.github.eutro.hierarchical-lang") version "1.1.1"
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
    from("src/add_something.json") {
        filter(
            mapOf(
                "transformer" to eutro.hierarchicallang.JsonTransformer {
                    it.addProperty("added", "value")
                    it
                },
                "outGson" to com.google.gson.GsonBuilder().setPrettyPrinting().create()
            ),
            eutro.hierarchicallang.JsonFilterReader::class.java
        )
    }
}
