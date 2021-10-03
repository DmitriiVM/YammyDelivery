plugins {
    id(PluginId.COMPOSE_CONVENTION)
}

dependencies {
    implementation(project(ModulePath.Core.UI))
    implementation(project(ModulePath.Core.UTILS))
    implementation(project(ModulePath.Core.NETWORK))
    implementation(project(ModulePath.Core.DATABASE))
    implementation(project(ModulePath.Core.DATASTORE))
    implementation(project(ModulePath.Feature.DRAWER_API))
    implementation(project(ModulePath.Services.UPDATE))
    implementation(project(ModulePath.NAVIGATION))

    implementation(libs.android.core)

    implementation(libs.bundles.compose)
    implementation(libs.compose.icons)
    implementation(libs.compose.constraintLayout)
    implementation(libs.accompanist.insets)

    implementation(libs.lifecycle.livedata)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.coroutines)
}