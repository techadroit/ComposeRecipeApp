plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
//    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin{


    plugins {
        register("androidApplication") {
            id = "android.application"
            implementationClass = "com.buildlogic.convention.AndroidApplicationConventionPlugin"
        }
        register("androidModuleLibrary") {
            id = "android.module.library"
            implementationClass = "com.buildlogic.convention.plugins.AndroidLibraryConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "android.application.compose"
            implementationClass = "com.buildlogic.convention.plugins.AndroidComposeConventionPlugin"
        }
        register("coreAndroid") {
            id = "android.application.core"
            implementationClass = "com.buildlogic.convention.plugins.AndroidCoreConventionPlugin"
        }
        register("androidApplicationDatabase") {
            id = "android.application.database"
            implementationClass = "com.buildlogic.convention.plugins.AndroidDatabaseConventionPlugin"
        }
        register("androidApplicationHilt") {
            id = "android.application.hilt"
            implementationClass = "com.buildlogic.convention.plugins.AndroidHiltConventionPlugin"
        }
        register("androidApplicationNetwork") {
            id = "android.application.network"
            implementationClass = "com.buildlogic.convention.plugins.AndroidNetworkConventionPlugin"
        }
        register("androidApplicationTesting") {
            id = "android.application.testing"
            implementationClass = "com.buildlogic.convention.plugins.AndroidTestingConventionPlugin"
        }
    }
}
