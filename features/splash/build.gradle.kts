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

    implementation(libs.android.core)
    implementation(libs.android.appcompat)
    implementation(libs.android.fragment)

    implementation(libs.bundles.compose)
    implementation(libs.compose.lottie)

    implementation(libs.lifecycle.viewModel)
    kapt(libs.lifecycle.compiler)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
}