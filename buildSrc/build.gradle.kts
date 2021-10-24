plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.0-alpha08")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
    implementation("com.google.gms:google-services:4.3.10")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.18.1")
}