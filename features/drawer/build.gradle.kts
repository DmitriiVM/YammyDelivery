plugins {
    id(PluginId.COMPOSE_CONVENTION)
    id(PluginId.KOTLIN_KAPT)
    id(PluginId.DAGGER_HILT)
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.utils)
    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.navigation)

    implementation(libs.bundles.compose)
    implementation(libs.compose.icons)
    implementation(libs.compose.viewModel)
    implementation(libs.accompanist.insets)
    implementation(libs.compose.coil)

    implementation(libs.lifecycle.viewModel)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)
}