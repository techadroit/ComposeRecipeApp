package com.buildlogic.convention.plugins

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidDatabaseConventionPlugin : BasePlugins(){

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val room = libs.findLibrary("android-room").get()
            val roomCompiler = libs.findLibrary("android-room-compiler").get()
            val roomKtx = libs.findLibrary("android-room-ktx").get()

            dependencies {
                add("implementation", room)
//                add("kapt", roomCompiler)
                add("implementation", roomKtx)
            }
        }
    }
}
