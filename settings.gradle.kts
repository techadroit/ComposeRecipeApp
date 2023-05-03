pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "ComposeRecipeApp"
include(":app")
include(":feature")
include(":archerviewmodel")
include(":core:network")
include(":core:platform")
include(":data:repository")
include(":domain")
include(":domain:recipe")
include(":domain:common")
include(":domain:favourite")
include(":core:navigation")
include(":app-navigation")
include(":feature:recipe-video")
include(":feature:common")
include(":feature:common-ui")
include(":core:themes")
