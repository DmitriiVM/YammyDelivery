rootProject.name = "YammyDelivery"

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

includeBuild("build-logic")

include(
    ":app",
    ":features:splash-impl",
    ":features:splash-api",
    ":features:menu-impl",
    ":features:menu-api",
    ":features:cart-impl",
    ":features:cart-api",
    ":features:order-impl",
    ":features:order-api",
    ":features:auth-impl",
    ":features:auth-api",
    ":features:profile-impl",
    ":features:profile-api",
    ":features:notifications-impl",
    ":features:notifications-api",
    ":features:drawer-impl",
    ":features:drawer-api",
    ":services:notification",
    ":services:update",
    ":core:network",
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