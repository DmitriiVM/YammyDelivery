plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(":core:datastore"))
    implementation(project(":core:utils"))

    implementation(Libs.ANDROID_CORE)

    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)

    implementation(Libs.RETROFIT)
    implementation(Libs.RETROFIT_GSON_CONVERTER)
    implementation(Libs.GSON)
    implementation(Libs.LOGGING_INTERCEPTOR)

    implementation(Libs.DAGGER_HILT)
    kapt(Libs.DAGGER_HILT_COMPILER)
}