plugins {
    id("library-convention")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(":core:network"))

    implementation(libs.android.core)

    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)
}