plugins {
    id("android.module.library")
    id("android.application.hilt")
    id("android.application.network")
}

android {
    namespace = "com.core.network"

    buildFeatures{
        buildConfig = true
    }

}

kapt {
    generateStubs = true
    correctErrorTypes = true
}

dependencies {

    implementation(libs.kotlinx.coroutines.android)
}
