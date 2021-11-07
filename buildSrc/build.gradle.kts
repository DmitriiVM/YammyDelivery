plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.2.0-alpha03")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
    implementation("com.google.gms:google-services:4.3.10")
    implementation("com.squareup.sqldelight:gradle-plugin:1.5.0")
    implementation("org.jetbrains.kotlin:kotlin-serialization:1.5.21")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.18.1")
}