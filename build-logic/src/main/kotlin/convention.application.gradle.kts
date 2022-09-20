plugins {
    id("com.android.application")
    id("convention.base")
    id("convention.compose")
    id("com.google.gms.google-services")
}

android {
    defaultConfig {
        applicationId = "com.dvm.yammydelivery"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }
}