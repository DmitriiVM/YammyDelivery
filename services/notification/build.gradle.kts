plugins {
    id(PluginId.LIBRARY_CONVENTION)
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.utils)
    implementation(projects.core.datastore)
    implementation(projects.navigation)
    implementation(projects.features.notificationsApi)

    implementation(libs.android.core)
    implementation(libs.coroutines)

    implementation(libs.koin.core)
    implementation(libs.koin.android)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
}