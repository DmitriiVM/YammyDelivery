plugins {
    id(PluginId.LIBRARY_CONVENTION)
    id(PluginId.KOTLIN_KAPT)
}

dependencies {
    implementation(project(ModulePath.Core.NETWORK))

    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
}