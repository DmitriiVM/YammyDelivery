plugins {
    id(PluginId.COMPOSE_CONVENTION)
}

dependencies {
    implementation(project(ModulePath.Core.UTILS))
    implementation(project(ModulePath.Feature.DRAWER))

    implementation(libs.bundles.compose)
}