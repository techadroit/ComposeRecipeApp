package com.buildlogic.convention.plugins

import com.buildlogic.convention.extensions.addComposeBuildFeature
import com.buildlogic.convention.extensions.addComposeDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            addComposeBuildFeature()
            addComposeDependencies()
        }
    }
}
