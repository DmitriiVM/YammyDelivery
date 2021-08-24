tasks.register(name = "type", type = Delete::class) {
    delete(rootProject.buildDir)
}