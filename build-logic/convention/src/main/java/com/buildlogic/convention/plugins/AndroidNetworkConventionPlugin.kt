package com.buildlogic.convention.plugins

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidNetworkConventionPlugin : BasePlugins() {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                val retrofit = libs.findLibrary("retrofit").get()
                val retrofitCoroutineAdapter = libs.findLibrary("retrofit-coroutines-adapter").get()
                val okhttp = libs.findLibrary("okhttp").get()
                val retrofitLoggingInterceptor =
                    libs.findLibrary("retrofit-logging-interceptor").get()
                val retrofitGsonConverter = libs.findLibrary("retrofit-gson-converter").get()

                add("implementation", retrofit)
                add("implementation", retrofitCoroutineAdapter)
                add("implementation", okhttp)
                add("implementation", retrofitLoggingInterceptor)
                add("implementation", retrofitGsonConverter)
            }
        }
    }
}
