import com.android.build.gradle.BaseExtension

configure<BaseExtension> {
    compileSdkVersion(31)

    defaultConfig {
        minSdk = 23
        targetSdk = 31
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