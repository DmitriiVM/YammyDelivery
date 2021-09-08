plugins {
    id(PluginId.LIBRARY_CONVENTION)
}

dependencies {
    implementation(project(ModulePath.Core.UTILS))
    implementation(project(ModulePath.Core.NETWORK))
    implementation(project(ModulePath.Core.DATABASE))
    implementation(project(ModulePath.Core.DATASTORE))

    implementation(libs.android.core)

    implementation(libs.bundles.coroutines)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
}