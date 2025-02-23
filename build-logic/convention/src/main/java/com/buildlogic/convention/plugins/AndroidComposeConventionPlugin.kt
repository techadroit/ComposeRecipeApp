package com.buildlogic.convention.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.buildlogic.convention.extensions.addComposeBuildFeature
import com.buildlogic.convention.extensions.addComposeDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")
            val extension = extensions.getByType<ApplicationExtension>()
            addComposeBuildFeature(extension)
            addComposeDependencies()
        }
    }
}
