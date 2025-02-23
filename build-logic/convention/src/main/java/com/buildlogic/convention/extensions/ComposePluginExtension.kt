package com.buildlogic.convention.extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

fun Project.addComposeBuildFeature(extension: CommonExtension<*, *, *, *, *, *>) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    val composeVersion = libs.findVersion("composeVersion").get().toString()
    extension.apply {
        buildFeatures {
            compose = true
        }
    }
}

fun Project.addComposeDependencies() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    dependencies {
        val androidCompose = libs.findLibrary("androidx-compose").get()
        val androidComposeFoundation = libs.findLibrary("androidx-compose-foundation").get()
        val androidComposeMaterial = libs.findLibrary("androidx-compose-material").get()
        val androidComposeMaterialWindowSize =
            libs.findLibrary("androidx-compose-material-window-size").get()
        val androidComposeUiTooling = libs.findLibrary("androidx-compose-ui-tooling").get()
        val viewModelCompose = libs.findLibrary("androidx-lifecycle-viewModelCompose").get()
        val runtimeCompose = libs.findLibrary("androidx-lifecycle-runtimeCompose").get()
        val navigationCompose = libs.findLibrary("androidx-compose-navigation").get()
        val navigationFragment = libs.findLibrary("androidx-fragment-navigation").get()
        val andorixdWindow = libs.findLibrary("androidx-window").get()
        add("implementation", androidCompose)
        add("implementation", androidComposeFoundation)
        add("implementation", androidComposeMaterial)
        add("implementation", androidComposeMaterialWindowSize)
        add("implementation", androidComposeUiTooling)
        add("implementation", viewModelCompose)
        add("implementation", runtimeCompose)
        add("implementation", navigationCompose)
        add("implementation", navigationFragment)
        add("implementation", andorixdWindow)

        val androidComposeUiTestJunit = libs.findLibrary("androidx-compose-ui-test-junit").get()
        val androidComposeUiTestManifest =
            libs.findLibrary("androidx-compose-ui-test-manifest").get()
        add("androidTestImplementation", androidComposeUiTestJunit)
        add("androidTestImplementation", androidComposeUiTestManifest)
    }
}
