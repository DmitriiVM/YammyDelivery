plugins {
    id(PluginId.LIBRARY_CONVENTION)
    id(PluginId.KOTLIN_KAPT)
    id(PluginId.DAGGER_HILT)
    id(PluginId.KOTLIN_PARCELIZE)
}

dependencies {
    implementation(libs.android.core)
    implementation(libs.coroutines)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)
}