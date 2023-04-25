plugins {
    id("android.module.library")
    id("android.application.network")
    id("android.application.hilt")
}

android {
    namespace = "com.core.network"

    buildFeatures{
        buildConfig = true
    }

}
