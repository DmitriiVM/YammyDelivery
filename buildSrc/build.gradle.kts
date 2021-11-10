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
    implementation(lib.plugin.hilt)
    implementation(lib.plugin.safeargs)
    implementation(lib.plugin.google.services)
    implementation(lib.plugin.detekt)
}