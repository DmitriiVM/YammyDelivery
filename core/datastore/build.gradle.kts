plugins {
    id(PluginId.LIBRARY_CONVENTION)
    id(PluginId.KOTLIN_KAPT)
    id(PluginId.DAGGER_HILT)
}

dependencies {
    implementation(libs.android.core)
    implementation(libs.datastore)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)
}