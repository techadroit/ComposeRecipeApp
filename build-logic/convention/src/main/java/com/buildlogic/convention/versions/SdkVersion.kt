package com.buildlogic.convention.versions

import com.android.build.api.dsl.ApplicationExtension

object SdkVersion {
    val COMPILE_SDK = 33
    val TARGET_SDK = 33
    val MIN_SDK = 24
}

fun ApplicationExtension.addSdks(){
    compileSdk = SdkVersion.COMPILE_SDK
    defaultConfig.apply {
        targetSdk = SdkVersion.TARGET_SDK
        minSdk = SdkVersion.MIN_SDK
    }
}
