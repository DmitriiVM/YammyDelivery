import com.android.build.gradle.BaseExtension
import java.io.FileInputStream
import java.util.*

val properties = Properties()
val propertiesFile: File = rootProject.file("apikey.properties")
properties.load(FileInputStream(propertiesFile))

configure<BaseExtension> {
    compileSdkVersion(31)

    defaultConfig {
        minSdk = 23
        targetSdk = 31

        resValue("string", "google_key", properties.getProperty("GOOGLE_API_KEY"))
    }

    buildTypes {
        getByName("release") {
            minifyEnabled(true)
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf(
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=kotlinx.coroutines.FlowPreview",
                "-Xopt-in=kotlin.Experimental",
                "-Xopt-in=kotlin.RequiresOptIn"
            )
        }
    }
}