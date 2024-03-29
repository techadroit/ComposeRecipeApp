plugins {
    id("android.module.library")
    id("android.library.compose")
    id("android.application.hilt")
}
android {
    namespace = "com.feature.common"
}

dependencies {

    implementation(project(":state_manager"))
    implementation(project(":core:navigation"))
    implementation(project(":core:platform"))
    implementation(libs.image.loading)
}
