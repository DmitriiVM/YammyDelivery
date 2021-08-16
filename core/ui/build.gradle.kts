plugins {
    id("compose-convention")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(libs.android.core)
    implementation(libs.android.appcompat)

    implementation(libs.bundles.compose)
    implementation(libs.compose.icons)
    implementation(libs.compose.iconsExtended)
    implementation(libs.compose.constraintLayout)
    implementation(libs.compose.lottie)
    implementation(libs.compose.coil)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)
}