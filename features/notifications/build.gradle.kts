plugins {
    id(PluginId.COMPOSE_CONVENTION)
    id(PluginId.KOTLIN_KAPT)
    id(PluginId.DAGGER_HILT)
    id(PluginId.KOTLIN_PARCELIZE)
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.utils)
    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.features.drawerApi)
    implementation(projects.navigation)

    implementation(libs.android.core)
    implementation(libs.android.appcompat)

    implementation(libs.bundles.compose)
    implementation(libs.compose.icons)
    implementation(libs.compose.constraintLayout)
    implementation(libs.compose.viewModel)
    implementation(libs.compose.coil)
    implementation(libs.accompanist.insets)
    implementation(libs.compose.navigationHilt)

    implementation(libs.lifecycle.viewModel)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
}