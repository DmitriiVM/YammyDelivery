import com.android.build.gradle.BaseExtension

configure<BaseExtension> {

    plugins {
        id(PluginId.KOTLIN_ANDROID)
        id(PluginId.DETEKT)
    }

    compileSdkVersion(31)

    defaultConfig {
        minSdk = 23
        targetSdk = 31
    }
}

tasks.register<io.gitlab.arturbosch.detekt.Detekt>("detektAll") {
    parallel = true
    setSource(files(projectDir))
    config.from(files("$rootDir/detekt.yml"))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xopt-in=kotlinx.coroutines.FlowPreview",
            "-Xopt-in=kotlin.Experimental",
            "-Xopt-in=kotlin.RequiresOptIn"
        )
    }
}