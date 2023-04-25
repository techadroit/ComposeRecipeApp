package com.buildlogic.convention.plugins

import com.android.build.api.dsl.LibraryExtension
import com.buildlogic.convention.options.addCompileOptions
import com.buildlogic.convention.options.addKotlinOptions
import com.buildlogic.convention.versions.SdkVersion
import com.buildlogic.convention.versions.addSdks
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryConventionPlugin  : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.kapt")
                apply("org.jetbrains.kotlin.android")
            }
            val extension = extensions.getByType<LibraryExtension>()
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extension.apply {
                addSdks()
                addCompileOptions()
                addKotlinOptions()
                defaultConfig.targetSdk = SdkVersion.TARGET_SDK
            }
        }
    }
}
