plugins {
    id("android.module.library")
    id("android.library.compose")
}
android {
    namespace = "com.feature.common"
}

dependencies {

    implementation(project(":archerviewmodel"))
    implementation(project(":core:navigation"))
    implementation(project(":core:platform"))
    implementation(libs.image.loading)
}
