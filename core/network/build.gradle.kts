plugins {
    id(PluginId.LIBRARY_CONVENTION)
    id(PluginId.KOTLIN_KAPT)
    id(PluginId.DAGGER_HILT)
}

dependencies {
    implementation(project(ModulePath.Core.UTILS))
    implementation(project(ModulePath.Core.DATASTORE))

    implementation(libs.android.core)

    implementation(libs.coroutines)

    implementation(libs.retrofit)
    implementation(libs.gsonConverter)
    implementation(libs.gson)
    implementation(libs.loggingInterceptor)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)
}