plugins {
    id(PluginId.ANDROID_APPLICATION)
    id(PluginId.ANDROID_CONVENTION)
    id(PluginId.GOOGLE_SERVICES)
}

android {
    defaultConfig {
        applicationId = "com.dvm.yammydelivery"
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.1"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }
}

dependencies {
    implementation(project(ModulePath.Core.UI))
    implementation(project(ModulePath.Core.UTILS))
    implementation(project(ModulePath.Core.NETWORK))
    implementation(project(ModulePath.Core.DATABASE))
    implementation(project(ModulePath.Core.DATASTORE))
    implementation(project(ModulePath.Feature.MENU))
    implementation(project(ModulePath.Feature.DISH))
    implementation(project(ModulePath.Feature.DRAWER))
    implementation(project(ModulePath.Feature.CART))
    implementation(project(ModulePath.Feature.ORDER))
    implementation(project(ModulePath.Feature.AUTH))
    implementation(project(ModulePath.Feature.PROFILE))
    implementation(project(ModulePath.Feature.SPLASH))
    implementation(project(ModulePath.Feature.NOTIFICATIONS))
    implementation(project(ModulePath.Services.NOTIFICATION))
    implementation(project(ModulePath.Services.UPDATE))
    implementation(project(ModulePath.NAVIGATION))

    implementation(libs.android.core)
    implementation(libs.android.appcompat)
    implementation(libs.android.material)

    implementation(libs.bundles.compose)
    implementation(libs.compose.navigation)
    implementation(libs.accompanist.insets)

    implementation(libs.koin.core)
    implementation(libs.koin.android)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)

    debugImplementation(libs.leakCanary)
}