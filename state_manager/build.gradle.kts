

plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("kapt")
}

android {
    namespace = "com.state_manager"
    compileSdk = 34
    buildToolsVersion = "34.0.0"

    defaultConfig {
        minSdk = 24
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.android.coroutines.test)
    implementation(libs.android.lifecycle)
    implementation(libs.kotlin.turbine)
    implementation(libs.android.junit)
    implementation(libs.android.espresso.core)
    implementation(libs.androidx.lifecycle.viewModelCompose)
}
