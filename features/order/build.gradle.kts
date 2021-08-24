plugins {
    id(PluginId.COMPOSE_CONVENTION)
    id(PluginId.KOTLIN_KAPT)
    id(PluginId.DAGGER_HILT)
    id(PluginId.SAFE_ARGS)
    id(PluginId.KOTLIN_PARCELIZE)
}

dependencies {
    implementation(project(ModulePath.Core.UI))
    implementation(project(ModulePath.Core.UTILS))
    implementation(project(ModulePath.Core.NETWORK))
    implementation(project(ModulePath.Core.DATABASE))
    implementation(project(ModulePath.Core.DATASTORE))
    implementation(project(ModulePath.Core.UPDATE_SERVICE))
    implementation(project(ModulePath.Feature.APP_MENU_API))
    implementation(project(ModulePath.NAVIGATION))

    implementation(libs.android.core)
    implementation(libs.android.appcompat)

    implementation(libs.bundles.compose)
    implementation(libs.compose.icons)
    implementation(libs.compose.iconsExtended)
    implementation(libs.compose.viewModel)
    implementation(libs.compose.coil)
    implementation(libs.accompanist.insets)
    implementation(libs.compose.navigationHilt)

    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewModel)
    implementation(libs.lifecycle.runtime)
    kapt(libs.lifecycle.compiler)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation(libs.google.maps)
    implementation(libs.google.location)
}