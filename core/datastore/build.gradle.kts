plugins {
    id(PluginId.CONVENTION_LIBRARY)
}

dependencies {
    implementation(projects.core.utils)

    implementation(libs.android.core)
    implementation(libs.datastore)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
}