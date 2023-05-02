package com.buildlogic.convention.plugins

import com.android.build.api.dsl.LibraryExtension
import com.buildlogic.convention.extensions.addComposeBuildFeature
import com.buildlogic.convention.extensions.addComposeDependencies
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposePlugin: BasePlugins() {
    override fun apply(target: Project) {
        with(target) {
            // Make sure library module "com.android.library"
            val extension = extensions.getByType<LibraryExtension>()
            addComposeBuildFeature(extension)
            addComposeDependencies()
        }
    }
}
