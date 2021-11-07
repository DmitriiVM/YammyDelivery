plugins {
    id(PluginId.COMPOSE_CONVENTION)
}

dependencies {
    implementation(projects.core.utils)
    implementation(projects.features.drawer)

    implementation(libs.bundles.compose)
}