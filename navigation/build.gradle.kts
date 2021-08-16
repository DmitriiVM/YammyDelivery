plugins {
    id("library-convention")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

dependencies {
    implementation(libs.android.core)
    implementation(libs.bundles.coroutines)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)
}