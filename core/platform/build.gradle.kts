plugins {
    id("android.module.library")
}

android {
    namespace = "com.core.platform"
}

dependencies {

    implementation(libs.kotlinx.coroutines.android)
}
