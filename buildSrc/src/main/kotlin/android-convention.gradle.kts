import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.DefaultConfig
import java.io.FileInputStream
import java.util.*

configure<BaseExtension> {

    plugins {
        id(PluginId.KOTLIN_ANDROID)
        id(PluginId.DETEKT)
    }

    compileSdkVersion(31)

    defaultConfig {
        minSdk = 23
        targetSdk = 31

        buildGoogleApiKey()
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

fun DefaultConfig.buildGoogleApiKey() {
    val properties = Properties()
    val propertiesFile: File = rootProject.file("apikey.properties")
    properties.load(FileInputStream(propertiesFile))
    resValue("string", "google_key", properties.getProperty("GOOGLE_API_KEY"))
}