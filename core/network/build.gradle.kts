plugins {
    id(PluginId.LIBRARY_CONVENTION)
    id(PluginId.KOTLIN_SERIALIZATION)
}

dependencies {
    implementation(projects.core.utils)
    implementation(projects.core.datastore)

    implementation(libs.coroutines)

    implementation(libs.bundles.ktor)

    implementation(libs.koin.core)
}