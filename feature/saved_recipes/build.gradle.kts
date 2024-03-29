plugins {
    id("android.module.library")
    id("android.library.compose")
    id("android.application.hilt")
}
android {
    namespace = "com.feature.saved.recipes"
}

dependencies {

    implementation(project(":state_manager"))
    implementation(project(":domain:common"))
    implementation(project(":domain:recipe"))
    implementation(project(":domain:favourite"))
    implementation(project(":data:repository"))
    implementation(project(":app-navigation"))
    implementation(project(":core:navigation"))
    implementation(project(":core:platform"))
    implementation(project(":design_system:themes"))
    implementation(project(":feature:common"))
    implementation(project(":feature:common-ui"))
    implementation("com.google.android.exoplayer:exoplayer:2.14.1")
    implementation(libs.image.loading)
}
