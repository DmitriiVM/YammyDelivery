import java.io.FileInputStream
import java.util.Properties

val properties = Properties()
val propertiesFile: File = rootProject.file("apikey.properties")
properties.load(FileInputStream(propertiesFile))

plugins {
    id(PluginId.COMPOSE_CONVENTION)
    id(PluginId.KOTLIN_PARCELIZE)
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
    implementation(project(ModulePath.Core.UI))
    implementation(project(ModulePath.Core.UTILS))
    implementation(project(ModulePath.Core.NETWORK))
    implementation(project(ModulePath.Core.DATABASE))
    implementation(project(ModulePath.Core.DATASTORE))
    implementation(project(ModulePath.Feature.DRAWER_API))
    implementation(project(ModulePath.Services.UPDATE))
    implementation(project(ModulePath.NAVIGATION))

    implementation(libs.android.core)
    implementation(libs.android.appcompat)

    implementation(libs.activity.compose)
    implementation(libs.bundles.compose)
    implementation(libs.compose.icons)
    implementation(libs.compose.coil)

    implementation(libs.accompanist.insets)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pagerIndicators)

    implementation(libs.lifecycle.livedata)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.navigation.ui)

    implementation(libs.google.maps)
    implementation(libs.google.mapsKtx)
    implementation(libs.google.location)
}