plugins {
    id(PluginId.LIBRARY_CONVENTION)
}

dependencies {
    implementation(project(ModulePath.Core.UTILS))

    implementation(libs.android.core)
    implementation(libs.datastore)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
}