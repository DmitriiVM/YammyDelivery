plugins {
    id(PluginId.ANDROID_APPLICATION)
    id(PluginId.COMMON_CONVENTION)
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
    implementation(projects.core.network)
    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.features.menu)
    implementation(projects.features.dish)
    implementation(projects.features.drawer)
    implementation(projects.features.cart)
    implementation(projects.features.order)
    implementation(projects.features.auth)
    implementation(projects.features.profile)
    implementation(projects.features.splash)
    implementation(projects.features.notifications)
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

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)

    debugImplementation(libs.leakCanary)
}