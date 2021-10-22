plugins {
    id(PluginId.COMPOSE_CONVENTION)
}

dependencies {
    implementation(project(ModulePath.Core.UI))

    implementation(libs.android.core)

    implementation(libs.bundles.compose)
    implementation(libs.compose.navigation)
}