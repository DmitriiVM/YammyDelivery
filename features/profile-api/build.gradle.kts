plugins {
    id(PluginId.CONVENTION_LIBRARY_COMPOSE)
}

dependencies {
    implementation(libs.coroutines)
    implementation(libs.bundles.compose)
    implementation(libs.compose.navigation)
}