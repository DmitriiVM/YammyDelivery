plugins {
    id("com.android.application")
    id("android-convention")
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