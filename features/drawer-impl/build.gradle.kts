plugins {
    id(PluginId.COMPOSE_CONVENTION)
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.utils)
    implementation(projects.core.datastore)
    implementation(projects.navigation)
    implementation(projects.features.profileApi)
    implementation(projects.features.orderApi)
    implementation(projects.features.cartApi)
    implementation(projects.features.menuApi)
    implementation(projects.features.drawerApi)
    implementation(projects.features.notificationsApi)

    implementation(libs.bundles.compose)
    implementation(libs.compose.icons)
    implementation(libs.compose.iconsExtended)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
}