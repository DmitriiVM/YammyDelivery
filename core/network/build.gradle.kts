plugins {
    id(PluginId.LIBRARY_CONVENTION)
    id(PluginId.KOTLIN_SERIALIZATION)
}

dependencies {
    implementation(project(ModulePath.Core.UTILS))
    implementation(project(ModulePath.Core.DATASTORE))

    implementation(libs.coroutines)

    implementation(libs.bundles.ktor)

    implementation(libs.koin.core)
}