plugins {
    id(PluginId.LIBRARY_CONVENTION)
    id(PluginId.KOTLIN_KAPT)
    id(PluginId.DAGGER_HILT)
}

dependencies {
    implementation(projects.core.utils)
    implementation(projects.core.network)
    implementation(projects.core.database)
    implementation(projects.core.datastore)

    implementation(libs.android.core)
    implementation(libs.coroutines)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)
}