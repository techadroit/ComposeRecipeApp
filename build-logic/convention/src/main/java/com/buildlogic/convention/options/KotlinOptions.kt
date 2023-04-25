package com.buildlogic.convention.options

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.buildlogic.convention.extensions.kotlinOptions
import org.gradle.api.JavaVersion

fun CommonExtension<*, *, *, *>.addKotlinOptions(){
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}
