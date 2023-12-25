plugins {
    id("android.module.library")
    id("android.library.compose")
    id("android.application.hilt")
    id("android.application.testing")
}
android {
    namespace = "com.feature.settings"
}

dependencies {

    implementation(project(":state_manager"))
    implementation(project(":state_manager_test"))
    implementation(project(":state_manager_ui"))
    implementation(project(":data:repository"))
    implementation(project(":domain:common"))
    implementation(project(":domain:favourite"))
    implementation(project(":domain:recipe"))
    implementation(project(":app-navigation"))
    implementation(project(":core:navigation"))
    implementation(project(":core:platform"))
    implementation(project(":feature:common"))
    implementation(project(":feature:common-ui"))
    implementation(project(":feature:user_interest"))
    implementation(project(":design_system:ui"))
    implementation("com.google.android.exoplayer:exoplayer:2.14.1")
    implementation(libs.image.loading)
}
