plugins {
    id(PluginId.COMPOSE_CONVENTION)
    id(PluginId.KOTLIN_KAPT)
}

dependencies {
    implementation(projects.core.utils)
    implementation(projects.features.drawer)

    implementation(libs.bundles.compose)
}