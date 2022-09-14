plugins {
    id(PluginId.CONVENTION_LIBRARY_COMPOSE)
}

dependencies {
    implementation(projects.core.ui)
    implementation(libs.compose.navigation)
}