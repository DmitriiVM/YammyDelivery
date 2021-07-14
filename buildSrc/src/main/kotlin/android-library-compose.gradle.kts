plugins {
    id("android-library-base")
}

android {

    kotlinOptions {
        useIR = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE
    }
}