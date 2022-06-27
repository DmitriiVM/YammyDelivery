plugins {
    id(PluginId.COMPOSE_CONVENTION)
}

dependencies {
    implementation(projects.core.utils)

    implementation(libs.bundles.compose)
}