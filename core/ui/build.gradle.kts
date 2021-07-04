plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
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
    implementation(Libs.ANDROID_CORE)
    implementation(Libs.ANDROID_APPCOMPAT)

    implementation(Libs.COMPOSE_UI)
    implementation(Libs.COMPOSE_UI_TOOLING)
    implementation(Libs.COMPOSE_FOUNDATION)
    implementation(Libs.COMPOSE_MATERIAL)
    implementation(Libs.COMPOSE_ICONS)
    implementation(Libs.COMPOSE_ICONS_EXTENDED)
    implementation(Libs.COMPOSE_CONSTRAINT_LAYOUT)
    implementation(Libs.COMPOSE_LOTTIE)

    implementation(Libs.DAGGER_HILT)
    kapt(Libs.DAGGER_HILT_COMPILER)
}