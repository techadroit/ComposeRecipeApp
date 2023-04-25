package com.buildlogic.convention

import com.android.build.api.dsl.ApplicationExtension
import com.buildlogic.convention.options.addCompileOptions
import com.buildlogic.convention.options.addKotlinOptions
import com.buildlogic.convention.versions.SdkVersion
import com.buildlogic.convention.versions.addAppVersion
import com.buildlogic.convention.versions.addSdks
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.kapt")
                apply("org.jetbrains.kotlin.android")
            }
            val extension = extensions.getByType<ApplicationExtension>()

            extension.apply {
                addSdks()
                addAppVersion()
                addCompileOptions()
                addKotlinOptions()
                defaultConfig.targetSdk = SdkVersion.TARGET_SDK
            }
        }
    }
}

