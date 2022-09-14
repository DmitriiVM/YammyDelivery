plugins {
    id(PluginId.CONVENTION_LIBRARY_COMPOSE)
}

dependencies {
    implementation(projects.core.ui)

    implementation(libs.android.core)

    implementation(libs.bundles.compose)
    implementation(libs.compose.navigation)

    implementation(libs.sqldelight.androidDriver)
}