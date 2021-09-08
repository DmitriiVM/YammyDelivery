plugins {
    id(PluginId.LIBRARY_CONVENTION)
    id(PluginId.KOTLIN_PARCELIZE)
}

dependencies {
    implementation(libs.android.core)
    implementation(libs.bundles.coroutines)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
}