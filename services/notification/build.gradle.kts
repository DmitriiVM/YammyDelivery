plugins {
    id(PluginId.LIBRARY_CONVENTION)
    id(PluginId.KOTLIN_KAPT)
    id(PluginId.DAGGER_HILT)
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.utils)
    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.navigation)

    implementation(libs.android.core)
    implementation(libs.coroutines)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
}