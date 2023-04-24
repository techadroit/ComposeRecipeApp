package com.buildlogic.convention.plugins

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidCoreConventionPlugin : BasePlugins() {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val lifecycleRuntime = libs.findLibrary("androidx-lifecycle-runtime").get()
            val coreKtx = libs.findLibrary("androidx-core-ktx").get()
            val androidLifecycle = libs.findLibrary("android-lifecycle").get()
            val appcompat = libs.findLibrary("android-appcompat").get()
            val androidMaterial = libs.findLibrary("android-material").get()

            dependencies {
                add("implementation", lifecycleRuntime)
                add("implementation", coreKtx)
                add("implementation", androidLifecycle)
                add("implementation", appcompat)
                add("implementation", androidMaterial)
            }
        }
    }
}
