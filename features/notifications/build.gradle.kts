plugins {
    id("android-library-compose")
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

    implementation(Libs.ANDROID_CORE)
    implementation(Libs.ANDROID_APPCOMPAT)

    implementation(Libs.COMPOSE_UI)
    implementation(Libs.COMPOSE_UI_TOOLING)
    implementation(Libs.COMPOSE_FOUNDATION)
    implementation(Libs.COMPOSE_MATERIAL)
    implementation(Libs.COMPOSE_CONSTRAINT_LAYOUT)
    implementation(Libs.COMPOSE_ICONS)
    implementation(Libs.COMPOSE_ICONS_EXTENDED)
    implementation(Libs.COMPOSE_VIEW_MODEL)
    implementation(Libs.COMPOSE_ACCOMPANIST_COIL)
    implementation(Libs.COMPOSE_ACCOMPANIST_INSETS)

    implementation(Libs.LIFECYCLE_VIEWMODEL)
    implementation(Libs.LIFECYCLE_RUNTIME)
    kapt(Libs.LIFECYCLE_COMPILER)

    implementation(Libs.DAGGER_HILT)
    kapt(Libs.DAGGER_HILT_COMPILER)

    implementation(Libs.NAVIGATION_FRAGMENT)
    implementation(Libs.NAVIGATION_UI)

    implementation(platform(Libs.FIREBASE_BOM))
    implementation(Libs.FIREBASE_MESSAGING)
}