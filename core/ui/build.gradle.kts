plugins {
    id(PluginId.COMPOSE_CONVENTION)
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
}