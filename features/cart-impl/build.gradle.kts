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
    implementation(projects.features.cartApi)
    implementation(projects.features.menuApi)
    implementation(projects.features.drawerApi)
    implementation(projects.navigation)

    implementation(libs.android.core)

    implementation(libs.bundles.compose)
    implementation(libs.compose.icons)
    implementation(libs.compose.coil)
    implementation(libs.compose.navigation)

    implementation(libs.lifecycle.livedata)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.bundles.ktor)

    implementation(libs.sqldelight.androidDriver)
    implementation(libs.sqldelight.coroutines)
}

sqldelight {
    database("CartDatabase") { }
}