plugins {
    id(PluginId.COMPOSE_CONVENTION)
    id(PluginId.KOTLIN_KAPT)
    id(PluginId.DAGGER_HILT)
    id(PluginId.SAFE_ARGS)
}

dependencies {
    implementation(project(ModulePath.Core.UI))
    implementation(project(ModulePath.Core.DATASTORE))
    implementation(project(ModulePath.Core.UPDATE_SERVICE))
    implementation(project(ModulePath.NAVIGATION))

    implementation(libs.android.core)
    implementation(libs.android.appcompat)
    implementation(libs.android.fragment)

    implementation(libs.bundles.compose)
    implementation(libs.compose.lottie)
    implementation(libs.compose.navigationHilt)

    implementation(libs.lifecycle.viewModel)
    kapt(libs.lifecycle.compiler)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
}