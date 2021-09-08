plugins {
    id("com.autonomousapps.dependency-analysis") version "0.77.0"
}

tasks.register(name = "type", type = Delete::class) {
    delete(rootProject.buildDir)
}