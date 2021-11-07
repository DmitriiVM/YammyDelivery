rootProject.name = "YammyDelivery"

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":app",
    ":features:splash",
    ":features:menu",
    ":features:dish",
    ":features:cart",
    ":features:order",
    ":features:auth",
    ":features:profile",
    ":features:notifications",
    ":features:drawer",
    ":features:drawer-api",
    ":services:notification",
    ":services:update",
    ":core:network",
    ":core:database",
    ":core:ui",
    ":core:utils",
    ":core:datastore",
    ":navigation"
)

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}