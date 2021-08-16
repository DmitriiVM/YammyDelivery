plugins {
    id("compose-convention")
    id("kotlin-kapt")
}

dependencies {
    implementation(project(":features:appmenu"))
    implementation(project(":core:utils"))

    implementation(libs.bundles.compose)
    implementation(libs.compose.viewModel)

    implementation(libs.lifecycle.viewModel)
    implementation(libs.lifecycle.runtime)
    kapt(libs.lifecycle.compiler)
}