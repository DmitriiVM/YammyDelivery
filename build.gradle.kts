buildscript {

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(Libs.ANDROID_GRADLE_PLUGIN)
        classpath(Libs.KOTLIN_GRADLE_PLUGIN)
        classpath(Libs.HILT_GRADLE_PLUGIN)
        classpath(Libs.SAFE_ARGS_PLUGIN)
        classpath(Libs.GOOGLE_SERVICES)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

subprojects {
    plugins.matching {
        it is com.android.build.gradle.AppPlugin ||
                it is com.android.build.gradle.LibraryPlugin
    }.whenPluginAdded {

        configure<com.android.build.gradle.BaseExtension> {

            compileSdkVersion(30)

            defaultConfig {
                minSdkVersion(23)
                targetSdkVersion(30)
            }

            buildTypes {
                getByName("release") {
                    minifyEnabled(false)
                }
            }

            compileOptions {
                sourceCompatibility(JavaVersion.VERSION_1_8)
                targetCompatibility(JavaVersion.VERSION_1_8)
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf(
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=kotlinx.coroutines.FlowPreview",
                "-Xopt-in=kotlin.Experimental",
                "-Xopt-in=kotlin.RequiresOptIn"
            )
        }
    }
}

tasks.register(name = "type", type = Delete::class) {
    delete(rootProject.buildDir)
}