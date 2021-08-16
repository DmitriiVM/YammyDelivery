plugins {
    id(PluginId.COMPOSE_CONVENTION)
    id(PluginId.KOTLIN_KAPT)
}

dependencies {
    implementation(project(ModulePath.Core.UTILS))
    implementation(project(ModulePath.Feature.APP_MENU))

    implementation(libs.bundles.compose)
    implementation(libs.compose.viewModel)

    implementation(libs.lifecycle.viewModel)
    implementation(libs.lifecycle.runtime)
    kapt(libs.lifecycle.compiler)
}