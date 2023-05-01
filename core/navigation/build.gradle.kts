plugins {
    id("android.module.library")
    id("android.library.compose")
}

android {
    namespace = "com.core.navigation"
}

kapt {
    generateStubs = true
    correctErrorTypes = true
}

dependencies {
    implementation(libs.androidx.compose.navigation)
}
