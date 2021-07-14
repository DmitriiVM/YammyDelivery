plugins {
    id("android-library-base")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:utils"))

    implementation(Libs.ANDROID_CORE)

    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)

    implementation(Libs.ROOM_RUNTIME)
    implementation(Libs.ROOM_KTX)
    kapt(Libs.ROOM_COMPILER)

    implementation(Libs.DAGGER_HILT)
    kapt(Libs.DAGGER_HILT_COMPILER)
}