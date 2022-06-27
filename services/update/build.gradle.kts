plugins {
    id(PluginId.LIBRARY_CONVENTION)
}

dependencies {
    implementation(projects.core.utils)
    implementation(projects.core.datastore)
    implementation(projects.features.orderApi)
    implementation(projects.features.profileApi)
    implementation(projects.features.menuApi)

    implementation(libs.android.core)
    implementation(libs.coroutines)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
}