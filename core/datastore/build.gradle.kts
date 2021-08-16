plugins {
    id("library-convention")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(libs.android.core)
    implementation(libs.datastore)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)
}