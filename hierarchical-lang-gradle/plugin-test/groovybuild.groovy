tasks.register("copyTaskGroovy", Copy) {
    destinationDir = file("out")
    from("src/expand_me.json") {
        expandJson(it) {
            it.setPrettyPrinting()
        }
    }
    from("src/flatten_me.json") {
        flattenJson.invoke(it) {
            it.setPrettyPrinting()
        }
    }
}
