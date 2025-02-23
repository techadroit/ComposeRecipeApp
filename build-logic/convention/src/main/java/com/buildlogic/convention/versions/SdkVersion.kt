package com.buildlogic.convention.versions

import com.android.build.api.dsl.CommonExtension

object SdkVersion {
    val COMPILE_SDK = 34
    val TARGET_SDK = 34
    val MIN_SDK = 24
}

fun CommonExtension<*, *, *, *, *, *>.addSdks() {
    compileSdk = SdkVersion.COMPILE_SDK
    defaultConfig.apply {
        minSdk = SdkVersion.MIN_SDK
    }
}
