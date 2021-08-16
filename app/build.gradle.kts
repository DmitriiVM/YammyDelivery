plugins {
    id(PluginId.ANDROID_APPLICATION)
    id(PluginId.ANDROID_CONVENTION)
    id(PluginId.KOTLIN_ANDROID)
    id(PluginId.KOTLIN_KAPT)
    id(PluginId.SAFE_ARGS)
    id(PluginId.DAGGER_HILT)
    id(PluginId.GOOGLE_SERVICES)
}

android {
    defaultConfig {
        applicationId = "com.dvm.yammydelivery"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(project(ModulePath.Core.UI))
    implementation(project(ModulePath.Core.UTILS))
    implementation(project(ModulePath.Core.NETWORK))
    implementation(project(ModulePath.Core.DATABASE))
    implementation(project(ModulePath.Core.DATASTORE))
    implementation(project(ModulePath.Core.UPDATE_SERVICE))
    implementation(project(ModulePath.Feature.MENU))
    implementation(project(ModulePath.Feature.DISH))
    implementation(project(ModulePath.Feature.APP_MENU))
    implementation(project(ModulePath.Feature.CART))
    implementation(project(ModulePath.Feature.ORDER))
    implementation(project(ModulePath.Feature.AUTH))
    implementation(project(ModulePath.Feature.PROFILE))
    implementation(project(ModulePath.Feature.SPLASH))
    implementation(project(ModulePath.Feature.NOTIFICATIONS))
    implementation(project(ModulePath.NAVIGATION))

    implementation(libs.android.core)
    implementation(libs.android.appcompat)
    implementation(libs.android.material)

    implementation(libs.bundles.compose)
    implementation(libs.compose.icons)
    implementation(libs.compose.iconsExtended)

    implementation(libs.bundles.coroutines)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

    implementation(libs.retrofit)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
}