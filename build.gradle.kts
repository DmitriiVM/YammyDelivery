buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Libs.ANDROID_GRADLE_PLUGIN)
        classpath(Libs.KOTLIN_GRADLE_PLUGIN)
        classpath(Libs.HILT_GRADLE_PLUGIN)
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.3")
        classpath("com.google.gms:google-services:4.3.5")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register(name = "type", type = Delete::class) {
    delete(rootProject.buildDir)
}