import com.android.build.gradle.BaseExtension

configure<BaseExtension> {
    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = "1.2.0"
}