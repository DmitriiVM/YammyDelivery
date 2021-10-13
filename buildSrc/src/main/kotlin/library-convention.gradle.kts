plugins {
    id("com.android.library")
    id("android-convention")
    id("kotlin-android")
}

dependencies {
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")
}