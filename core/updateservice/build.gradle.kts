plugins {
    id(PluginId.LIBRARY_CONVENTION)
    id(PluginId.KOTLIN_KAPT)
    id(PluginId.DAGGER_HILT)
}

dependencies {
    implementation(project(ModulePath.Core.UTILS))
    implementation(project(ModulePath.Core.NETWORK))
    implementation(project(ModulePath.Core.DATABASE))
    implementation(project(ModulePath.Core.DATASTORE))

    implementation(libs.android.core)

    implementation(libs.bundles.coroutines)

    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)
}