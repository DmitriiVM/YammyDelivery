buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(":build-logic")
    }
}

tasks.register(name = "type", type = Delete::class) {
    delete(rootProject.buildDir)
}