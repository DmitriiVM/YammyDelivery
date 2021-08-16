plugins {
    id("compose-convention")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(":core:datastore"))
    implementation(project(":core:updateservice"))
    implementation(project(":core:ui"))
    implementation(project(":navigation"))

    implementation(Libs.ANDROID_CORE)
    implementation(Libs.ANDROID_APPCOMPAT)
    implementation(Libs.ANDROID_FRAGMENT)

    implementation(Libs.COMPOSE_UI)
    implementation(Libs.COMPOSE_UI_TOOLING)
    implementation(Libs.COMPOSE_FOUNDATION)
    implementation(Libs.COMPOSE_LOTTIE)

    implementation(Libs.LIFECYCLE_VIEWMODEL)
    kapt(Libs.LIFECYCLE_COMPILER)

    implementation(Libs.DAGGER_HILT)
    kapt(Libs.DAGGER_HILT_COMPILER)

    implementation(Libs.NAVIGATION_FRAGMENT)
    implementation(Libs.NAVIGATION_UI)
}