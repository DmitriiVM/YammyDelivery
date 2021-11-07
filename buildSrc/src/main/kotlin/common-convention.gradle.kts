import com.android.build.gradle.BaseExtension
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

tasks.register<Detekt>("detektAll") {
    parallel = true
    setSource(files(projectDir))
    config.from(files("$rootDir/detekt.yml"))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xopt-in=kotlinx.coroutines.FlowPreview",
            "-Xopt-in=kotlin.Experimental",
            "-Xopt-in=kotlin.RequiresOptIn"
        )
    }
}