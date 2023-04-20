package com.buildlogic.convention.versions

import com.android.build.api.dsl.ApplicationExtension

object AppVersion {
    val applicationId = "com.example.composerecipeapp"
    val versionCode = 1
    val versionName = "1.0"
}

fun ApplicationExtension.addAppVersion(){
    defaultConfig {
        applicationId = AppVersion.applicationId
        versionCode = AppVersion.versionCode
        versionName = AppVersion.versionName
    }
}
