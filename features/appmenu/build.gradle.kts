plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
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
    implementation(project(":core:datastore"))
    implementation(project(":core:database"))
    implementation(project(":core:ui"))
    implementation(project(":core:utils"))
    implementation(project(":navigation"))

    implementation(Libs.COMPOSE_UI)
    implementation(Libs.COMPOSE_UI_TOOLING)
    implementation(Libs.COMPOSE_FOUNDATION)
    implementation(Libs.COMPOSE_MATERIAL)
    implementation(Libs.COMPOSE_ICONS)
    implementation(Libs.COMPOSE_ICONS_EXTENDED)
    implementation(Libs.COMPOSE_VIEW_MODEL)
    implementation(Libs.COMPOSE_ACCOMPANIST_COIL)
    implementation(Libs.COMPOSE_ACCOMPANIST_INSETS)

    implementation(Libs.LIFECYCLE_VIEWMODEL)
    implementation(Libs.LIFECYCLE_RUNTIME)
    kapt(Libs.LIFECYCLE_COMPILER)

    implementation(Libs.DAGGER_HILT)
    kapt(Libs.DAGGER_HILT_COMPILER)
}