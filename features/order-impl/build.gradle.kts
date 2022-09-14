import java.io.FileInputStream
import java.util.Properties

val properties = Properties()
val propertiesFile: File = rootProject.file("apikey.properties")
properties.load(FileInputStream(propertiesFile))

plugins {
    id(PluginId.CONVENTION_LIBRARY_COMPOSE)
    id(PluginId.KOTLIN_PARCELIZE)
    id(PluginId.KOTLIN_SERIALIZATION)
    id(PluginId.SQLDELIGHT)
}

android {
    defaultConfig {
        resValue("string", "google_key", properties.getProperty("GOOGLE_API_KEY"))
    }
    buildFeatures {
        resValues = true
    }
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.utils)
    implementation(projects.core.network)
    implementation(projects.core.datastore)
    implementation(projects.features.orderApi)
    implementation(projects.features.cartApi)
    implementation(projects.features.drawerApi)
    implementation(projects.services.update)
    implementation(projects.navigation)

    implementation(libs.android.core)
    implementation(libs.android.appcompat)

    implementation(libs.activity.compose)
    implementation(libs.bundles.compose)
    implementation(libs.compose.icons)
    implementation(libs.compose.coil)
    implementation(libs.compose.navigation)

    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pagerIndicators)

    implementation(libs.lifecycle.livedata)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.bundles.ktor)

    implementation(libs.navigation.ui)

    implementation(libs.google.maps)
    implementation(libs.google.mapsKtx)
    implementation(libs.google.location)

    implementation(libs.sqldelight.androidDriver)
    implementation(libs.sqldelight.coroutines)

//    implementation(libs.plugin.serialization)
}

sqldelight {
    database("OrderDatabase") { }
}