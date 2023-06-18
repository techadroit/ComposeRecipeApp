plugins {
    id("android.module.library")
    id("android.library.compose")
}
android {
    namespace = "com.feature.common.ui" //no hypen in the namespace
}

dependencies {

    implementation(project(":state_manager"))
    implementation(project(":core:navigation"))
    implementation(project(":core:platform"))
    implementation(project(":design_system:themes"))
    implementation(project(":design_system:ui"))
    implementation(project(":feature:common"))
    implementation(project(":domain:common"))
    implementation(libs.image.loading)
}
