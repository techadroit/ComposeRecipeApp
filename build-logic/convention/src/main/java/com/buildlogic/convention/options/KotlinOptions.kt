package com.buildlogic.convention.options

import com.android.build.api.dsl.ApplicationExtension
import com.buildlogic.convention.extensions.kotlinOptions
import org.gradle.api.JavaVersion

fun ApplicationExtension.addKotlinOptions(){
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}
