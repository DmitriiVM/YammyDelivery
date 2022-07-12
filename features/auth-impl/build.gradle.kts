plugins {
    id(PluginId.COMPOSE_CONVENTION)
    id(PluginId.KOTLIN_SERIALIZATION)
    id(PluginId.SQLDELIGHT)
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.utils)
    implementation(projects.core.network)
    implementation(projects.core.datastore)
    implementation(projects.features.authApi)
    implementation(projects.features.drawerApi)
    implementation(projects.features.profileApi)
    implementation(projects.services.update)
    implementation(projects.navigation)

    implementation(libs.android.core)

    implementation(libs.bundles.compose)
    implementation(libs.compose.icons)
    implementation(libs.compose.constraintLayout)
    implementation(libs.compose.navigation)

    implementation(libs.lifecycle.livedata)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.coroutines)

    implementation(libs.bundles.ktor)

//    implementation(libs.plugin.serialization)
}

sqldelight {
    database("AuthDatabase") { }
}