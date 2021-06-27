plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

android {
    defaultConfig {
        applicationId = "com.dvm.yammydelivery"
        versionCode = 1
        versionName = "1.0"
    }

    kotlinOptions {
        useIR = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE
    }
}

dependencies {
    implementation(project(":features:menu"))
    implementation(project(":features:dish"))
    implementation(project(":features:appmenu"))
    implementation(project(":features:cart"))
    implementation(project(":features:order"))
    implementation(project(":features:auth"))
    implementation(project(":features:profile"))
    implementation(project(":features:appmenu"))
    implementation(project(":features:splash"))
    implementation(project(":features:notifications"))
    implementation(project(":core:ui"))
    implementation(project(":core:utils"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:updateservice"))
    implementation(project(":navigation"))

    implementation(Libs.ANDROID_CORE)
    implementation(Libs.ANDROID_APPCOMPAT)
    implementation(Libs.ANDROID_MATERIAL)

    implementation(Libs.COMPOSE_UI)
    implementation(Libs.COMPOSE_UI_TOOLING)
    implementation(Libs.COMPOSE_FOUNDATION)
    implementation(Libs.COMPOSE_MATERIAL)
    implementation(Libs.COMPOSE_ICONS)
    implementation(Libs.COMPOSE_ICONS_EXTENDED)

    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)

    implementation(Libs.DAGGER_HILT)
    kapt(Libs.DAGGER_HILT_COMPILER)

    implementation(Libs.ROOM_RUNTIME)
    implementation(Libs.ROOM_KTX)
    kapt(Libs.ROOM_COMPILER)

    implementation(Libs.RETROFIT)

    implementation(Libs.NAVIGATION_FRAGMENT)
    implementation(Libs.NAVIGATION_UI)

    implementation(platform(Libs.FIREBASE_BOM))
    implementation(Libs.FIREBASE_MESSAGING)
}