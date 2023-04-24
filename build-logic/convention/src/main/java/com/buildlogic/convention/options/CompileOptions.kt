package com.buildlogic.convention.options

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion

fun ApplicationExtension.addCompileOptions(){
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
