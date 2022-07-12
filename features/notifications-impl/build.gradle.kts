plugins {
    id(PluginId.COMPOSE_CONVENTION)
    id(PluginId.KOTLIN_PARCELIZE)
    id(PluginId.SQLDELIGHT)
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.utils)
    implementation(projects.core.datastore)
    implementation(projects.features.notificationsApi)
    implementation(projects.features.drawerApi)
    implementation(projects.navigation)

    implementation(libs.android.core)

    implementation(libs.bundles.compose)
    implementation(libs.compose.icons)
    implementation(libs.compose.iconsExtended)
    implementation(libs.compose.constraintLayout)
    implementation(libs.compose.coil)
    implementation(libs.compose.navigation)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)

    implementation(libs.sqldelight.androidDriver)
    implementation(libs.sqldelight.coroutines)
}

sqldelight {
    database("NotificationDatabase") { }
}