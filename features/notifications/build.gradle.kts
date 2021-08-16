plugins {
    id("compose-convention")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

dependencies {
    implementation(project(":core:datastore"))
    implementation(project(":core:database"))
    implementation(project(":core:ui"))
    implementation(project(":core:utils"))
    implementation(project(":features:appmenu-api"))
    implementation(project(":navigation"))

    implementation(libs.android.core)
    implementation(libs.android.appcompat)

    implementation(libs.bundles.compose)
    implementation(libs.compose.icons)
    implementation(libs.compose.iconsExtended)
    implementation(libs.compose.constraintLayout)
    implementation(libs.compose.viewModel)
    implementation(libs.compose.coil)
    implementation(libs.accompanist.insets)

    implementation(libs.lifecycle.viewModel)
    implementation(libs.lifecycle.runtime)
    kapt(libs.lifecycle.compiler)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
}