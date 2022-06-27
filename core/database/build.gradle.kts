plugins {
    id(PluginId.LIBRARY_CONVENTION)
    id(PluginId.SQLDELIGHT)
}

sqldelight {
    database("AppDatabase") { }
}

dependencies {
    implementation(libs.sqldelight.androidDriver)
    implementation(libs.sqldelight.coroutines)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
}