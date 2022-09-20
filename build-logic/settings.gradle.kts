enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    versionCatalogs {
        create("lib") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}