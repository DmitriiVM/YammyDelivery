plugins {
    id(PluginId.ANDROID_APPLICATION)
    id(PluginId.COMMON_CONVENTION)
    id(PluginId.COMPOSE_OPTIONS_CONVENTION)
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

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.utils)
    implementation(projects.core.datastore)
    implementation(projects.core.network)
    implementation(projects.features.menuImpl)
    implementation(projects.features.menuApi)
    implementation(projects.features.drawerImpl)
    implementation(projects.features.drawerApi)
    implementation(projects.features.cartImpl)
    implementation(projects.features.cartApi)
    implementation(projects.features.orderImpl)
    implementation(projects.features.orderApi)
    implementation(projects.features.authImpl)
    implementation(projects.features.authApi)
    implementation(projects.features.profileImpl)
    implementation(projects.features.profileApi)
    implementation(projects.features.splashImpl)
    implementation(projects.features.splashApi)
    implementation(projects.features.notificationsImpl)
    implementation(projects.features.notificationsApi)
    implementation(projects.services.notification)
    implementation(projects.services.update)
    implementation(projects.navigation)

    implementation(libs.android.core)
    implementation(libs.android.appcompat)
    implementation(libs.android.material)

    implementation(libs.bundles.compose)
    implementation(libs.compose.navigation)
    implementation(libs.accompanist.insets)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)

    debugImplementation(libs.leakCanary)
}