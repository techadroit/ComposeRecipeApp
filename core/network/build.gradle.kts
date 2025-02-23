plugins {
    id("android.module.library")
    id("android.application.hilt")
    id("android.application.network")
}

android {
    namespace = "com.core.network"

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }
}

kapt {
    generateStubs = true
    correctErrorTypes = true
}

dependencies {

    implementation(libs.kotlinx.coroutines.android)
    implementation(project(":core:platform"))
}
