package com.buildlogic.convention.plugins

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidHiltConventionPlugin : BasePlugins() {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val hiltNavigation = libs.findLibrary("androidx-hilt-navigation").get()
            val hilt = libs.findLibrary("androidx-hilt").get()
            val hiltCompiler = libs.findLibrary("androidx-hilt-compiler").get()

            dependencies {
                add("implementation", hiltNavigation)
                add("implementation", hilt)
                add("kapt", hiltCompiler)
            }
        }
    }
}
