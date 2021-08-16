import com.android.build.gradle.BaseExtension

configure<BaseExtension> {
    compileSdkVersion(30)

    defaultConfig {
        minSdk = 23
        targetSdk = 30
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