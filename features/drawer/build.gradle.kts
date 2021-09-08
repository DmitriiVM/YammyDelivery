plugins {
    id(PluginId.COMPOSE_CONVENTION)
    id(PluginId.KOTLIN_KAPT)
    id(PluginId.DAGGER_HILT)
}

dependencies {
    implementation(project(ModulePath.Core.UI))
    implementation(project(ModulePath.Core.UTILS))
    implementation(project(ModulePath.Core.DATABASE))
    implementation(project(ModulePath.Core.DATASTORE))
    implementation(project(ModulePath.NAVIGATION))

    implementation(libs.bundles.compose)
    implementation(libs.compose.icons)
    implementation(libs.compose.viewModel)
    implementation(libs.accompanist.insets)
    implementation(libs.compose.coil)

    implementation(libs.lifecycle.viewModel)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)
}