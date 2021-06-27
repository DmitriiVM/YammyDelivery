plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

dependencies {

    implementation(Libs.ANDROID_CORE)
    implementation(Libs.DATASTORE)

    implementation(Libs.DAGGER_HILT)
    kapt(Libs.DAGGER_HILT_COMPILER)
}