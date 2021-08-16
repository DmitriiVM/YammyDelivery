plugins {
    id("library-convention")
}

dependencies {
    implementation(project(":core:ui"))

    implementation(libs.android.core)
}