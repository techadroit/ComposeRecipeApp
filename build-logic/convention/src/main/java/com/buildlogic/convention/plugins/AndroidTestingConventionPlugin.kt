package com.buildlogic.convention.plugins

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

class AndroidTestingConventionPlugin : BasePlugins() {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val junit = libs.findLibrary("android-junit").get()
            val junitExt = libs.findLibrary("android-junit-ext").get()
            val espressoCore = libs.findLibrary("android-espresso-core").get()
            val coroutineTest = libs.findLibrary("android-coroutines-test").get()
            val androidMockk = libs.findLibrary("android-mockk-test").get()
//            val androidAgentMockk = libs.findLibrary("android-mockk-agent-test").get()
//            val junitMockk = libs.findLibrary("junit-mockk-test").get()
            val kotlinFixture = libs.findLibrary("kotlin-fixture").get()
            val turbine = libs.findLibrary("kotlin-turbine").get()

            dependencies {
                add("testImplementation", junit)
                add("androidTestImplementation", junitExt)
                add("androidTestImplementation", espressoCore)
                add("testImplementation", coroutineTest)
                add("testImplementation", androidMockk)
//                add("testImplementation", androidAgentMockk)
                add("androidTestImplementation", androidMockk)
//                add("testImplementation", junitMockk)
                add("testImplementation",kotlinFixture)
                add("testImplementation",turbine)
            }
        }
    }
}
