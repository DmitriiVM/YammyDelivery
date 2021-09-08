plugins {
    id(PluginId.LIBRARY_CONVENTION)
}

dependencies {
    implementation(project(ModulePath.Core.UTILS))
    implementation(project(ModulePath.Core.DATASTORE))

    implementation(libs.bundles.coroutines)

    implementation(libs.retrofit)
    implementation(libs.gsonConverter)
    implementation(libs.gson)
    implementation(libs.loggingInterceptor)

    implementation(libs.koin.core)
}