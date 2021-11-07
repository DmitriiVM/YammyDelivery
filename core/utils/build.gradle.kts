plugins {
    id(PluginId.COMPOSE_CONVENTION)
}

dependencies {
    implementation(projects.core.ui)

    implementation(libs.android.core)

    implementation(libs.bundles.compose)
    implementation(libs.compose.navigation)
}