
plugins {
    id("android.module.library")
    id("android.application.hilt")
    id("android.application.network")
    id("android.application.database")
}

android {
    namespace = "com.data.repository"
}

kapt {
    generateStubs = true
    correctErrorTypes = true
}

dependencies {

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.datastore)
    implementation(project(":core:platform"))
    implementation(project(":core:network"))
//    implementation(project(mapOf("path" to ":core:network")))
}
