plugins {
    id(PluginId.LIBRARY_CONVENTION)
}

dependencies {
    implementation(project(ModulePath.Core.UI))

    implementation(libs.android.core)
}