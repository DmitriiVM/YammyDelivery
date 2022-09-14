plugins {
    id(PluginId.CONVENTION_LIBRARY_COMPOSE)
    id(PluginId.KOTLIN_SERIALIZATION)
    id(PluginId.SQLDELIGHT)
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.utils)
    implementation(projects.core.network)
    implementation(projects.core.datastore)
    implementation(projects.features.profileApi)
    implementation(projects.features.drawerApi)
    implementation(projects.navigation)

    implementation(libs.bundles.ktor)

    implementation(libs.android.core)
    implementation(libs.android.appcompat)

    implementation(libs.bundles.compose)
    implementation(libs.compose.icons)
    implementation(libs.compose.iconsExtended)
    implementation(libs.compose.coil)
    implementation(libs.compose.navigation)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.sqldelight.androidDriver)
    implementation(libs.sqldelight.coroutines)
}

sqldelight {
    database("ProfileDatabase") { }
}