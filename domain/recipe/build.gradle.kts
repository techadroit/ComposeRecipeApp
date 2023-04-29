
plugins {
    id("android.module.library")
    id("android.application.hilt")
}

android {
    namespace = "com.domain.recipe"
}

kapt {
    generateStubs = true
    correctErrorTypes = true
}

dependencies {

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.datastore)
    implementation(project(":data:repository"))
    implementation(project(":core:platform"))
    implementation(project(":domain:common"))
}
