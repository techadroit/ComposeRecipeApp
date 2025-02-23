package com.buildlogic.convention.options

import com.android.build.api.dsl.CommonExtension
import com.buildlogic.convention.extensions.kotlinOptions
import org.gradle.api.JavaVersion

fun CommonExtension<*, *, *, *, *, *>.addKotlinOptions() {
    kotlinOptions {
//        val warningsAsErrors: String? by project
//        allWarningsAsErrors = warningsAsErrors.toBoolean()

        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlin.RequiresOptIn",
            // Enable experimental coroutines APIs, including Flow
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.coroutines.FlowPreview",
            "-opt-in=kotlin.Experimental",
        )
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}
