plugins {
    id("android.module.library")
    id("android.library.compose")
}
android {
    namespace = "com.design_system.common.ui" //no hypen in the namespace
}

dependencies {

    implementation(project(":archerviewmodel"))
    implementation(project(":core:navigation"))
    implementation(project(":core:platform"))
    implementation(project(":design_system:themes"))
    implementation(libs.image.loading)
}
