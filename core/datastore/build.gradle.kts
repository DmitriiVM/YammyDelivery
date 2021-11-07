plugins {
    id(PluginId.LIBRARY_CONVENTION)
}

dependencies {
    implementation(projects.core.utils)

    implementation(libs.android.core)
    implementation(libs.datastore)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
}