plugins {
    id("com.android.library")
    id("common-convention")
    id("kotlin-android")
}

dependencies {
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")
}