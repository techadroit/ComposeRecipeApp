plugins {
    id("android.module.library")
}

android {
    namespace = "com.core.platform"

    buildFeatures{
        buildConfig = true
    }

}

dependencies {

    implementation(libs.kotlinx.coroutines.android)
}
