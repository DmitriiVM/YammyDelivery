plugins {
    id(PluginId.COMPOSE_CONVENTION)
    id(PluginId.KOTLIN_KAPT)
    id(PluginId.DAGGER_HILT)
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.utils)
    implementation(projects.core.network)
    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.features.drawerApi)
    implementation(projects.services.update)
    implementation(projects.navigation)

    implementation(libs.android.core)
    implementation(libs.android.appcompat)

    implementation(libs.bundles.compose)
    implementation(libs.compose.viewModel)
    implementation(libs.accompanist.insets)
    implementation(libs.compose.navigationHilt)

    implementation(libs.lifecycle.livedata)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    implementation(libs.coroutines)
}