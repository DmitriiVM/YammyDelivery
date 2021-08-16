plugins {
    id("library-convention")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(":core:datastore"))
    implementation(project(":core:utils"))

    implementation(libs.android.core)

    implementation(libs.bundles.coroutines)

    implementation(libs.retrofit)
    implementation(libs.gsonConverter)
    implementation(libs.gson)
    implementation(libs.loggingInterceptor)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)
}