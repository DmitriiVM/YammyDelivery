object Versions {
    const val KOTLIN = "1.4.21"
    const val COMPOSE = "1.0.0-alpha09"
    const val COROUTINES = "1.4.2"
    const val RETROFIT = "2.9.0"
    const val GSON = "2.8.6"
    const val ROOM = "2.2.3"
}

object Libs {
    const val ANDROID_GRADLE_PLUGIN = "com.android.tools.build:gradle:7.0.0-alpha04"
    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"

    const val ANDROID_CORE = "androidx.core:core-ktx:1.3.2"
    const val ANDROID_APPCOMPAT = "androidx.appcompat:appcompat:1.2.0"
    const val ANDROID_MATERIAL = "com.google.android.material:material:1.3.0"
    const val ANDROID_LIFECYCLE = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-rc01"
    const val ANDROID_FRAGMENT = "androidx.fragment:fragment-ktx:1.2.5"

    const val COMPOSE_UI = "androidx.compose.ui:ui:${Versions.COMPOSE}"
    const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
    const val COMPOSE_FOUNDATION = "androidx.compose.foundation:foundation:${Versions.COMPOSE}"
    const val COMPOSE_MATERIAL = "androidx.compose.material:material:${Versions.COMPOSE}"
    const val COMPOSE_ICONS = "androidx.compose.material:material-icons-core:${Versions.COMPOSE}"
    const val COMPOSE_ICONS_EXTENDED = "androidx.compose.material:material-icons-extended:${Versions.COMPOSE}"

    const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
    const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"

    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val GSON = "com.google.code.gson:gson:${Versions.GSON}"

    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"


    const val JUNIT = "junit:junit:4.+"
    const val ANDROID_JUNIT = "androidx.test.ext:junit:1.1.2"
    const val ANDROID_ESPRESSO = "androidx.test.espresso:espresso-core:3.3.0"
}