plugins {
    id(PluginId.LIBRARY_CONVENTION)
    id(PluginId.KOTLIN_KAPT)
    id(PluginId.DAGGER_HILT)
}

dependencies {
    implementation(projects.core.utils)
    implementation(projects.core.datastore)

    implementation(libs.android.core)

    implementation(libs.coroutines)

    implementation(libs.retrofit)
    implementation(libs.gsonConverter)
    implementation(libs.gson)
    implementation(libs.loggingInterceptor)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)
}