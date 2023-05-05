
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.hilt.dagger) apply false
    id("com.android.library") version "7.4.0" apply false
    id("com.android.dynamic-feature") version "7.4.0" apply false
}
