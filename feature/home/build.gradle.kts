plugins {
    id("android.module.library")
    id("android.library.compose")
    id("android.application.hilt")
    id("android.application.testing")
}
android {
    namespace = "com.feature.recipe.home"
}

dependencies {

    implementation(project(":state_manager"))
    implementation(project(":state_manager_test"))
    implementation(project(":state_manager_ui"))
    implementation(project(":domain:common"))
    implementation(project(":domain:recipe"))
    implementation(project(":app-navigation"))
    implementation(project(":core:navigation"))
    implementation(project(":core:platform"))
    implementation(project(":design_system:themes"))
    implementation(project(":design_system:ui"))
    implementation(project(":design_system:ui"))
    implementation(project(":feature:common"))
    implementation(project(":feature:common-ui"))
    implementation(libs.androidx.compose.constraintLayout)
    implementation(libs.image.loading)
}
