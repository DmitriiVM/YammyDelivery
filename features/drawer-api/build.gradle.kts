plugins {
    id(PluginId.CONVENTION_LIBRARY_COMPOSE)
}

dependencies {
    implementation(projects.core.utils)

    implementation(libs.bundles.compose)
}