plugins {
    id("com.android.library")
    id("convention.base")
    id("kotlin-android")
}

dependencies {
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")
}