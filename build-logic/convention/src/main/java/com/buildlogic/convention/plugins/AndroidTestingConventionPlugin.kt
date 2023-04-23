package com.buildlogic.convention.plugins

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidTestingConventionPlugin : BasePlugins() {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val junit = libs.findLibrary("android-junit").get()
            val junitExt = libs.findLibrary("android-junit-ext").get()
            val espressoCore = libs.findLibrary("android-espresso-core").get()
            val coroutineTest = libs.findLibrary("android-coroutines-test").get()
            val androidMockk = libs.findLibrary("android-mockk-test").get()

            dependencies {
                add("testImplementation", junit)
                add("androidTestImplementation", junitExt)
                add("androidTestImplementation", espressoCore)
                add("testImplementation", coroutineTest)
                add("testImplementation", androidMockk)
                add("androidTestImplementation", androidMockk)
            }
        }
    }
}
