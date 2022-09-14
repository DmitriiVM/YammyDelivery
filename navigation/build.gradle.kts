plugins {
    id(PluginId.CONVENTION_LIBRARY)
    id(PluginId.KOTLIN_PARCELIZE)
}

dependencies {
    implementation(libs.android.core)
    implementation(libs.coroutines)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
}