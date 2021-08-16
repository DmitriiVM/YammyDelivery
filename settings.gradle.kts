rootProject.name = "YammyDelivery"

include(
    ":app",
    ":features:splash",
    ":features:menu",
    ":features:dish",
    ":features:cart",
    ":features:order",
    ":features:auth",
    ":features:profile",
    ":features:appmenu",
    ":features:notifications",
    ":features:appmenu-api",
    ":core:network",
    ":core:database",
    ":core:ui",
    ":core:updateservice",
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

enableFeaturePreview("VERSION_CATALOGS")