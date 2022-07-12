plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(lib.plugin.androidGradle)
    implementation(lib.plugin.kotlinGradle)
    implementation(lib.plugin.kotlinSerialization)
    implementation(lib.plugin.google.services)
    implementation(lib.plugin.sqldelight)
    implementation(lib.plugin.detekt)
}