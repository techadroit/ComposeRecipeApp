plugins {
    id("android.module.library")
    id("android.library.compose")
}
android {
    namespace = "com.feature.common.ui" //no hypen in the namespace
}

dependencies {

    implementation(project(":archerviewmodel"))
    implementation(project(":core:navigation"))
    implementation(project(":core:platform"))
    implementation(project(":core:themes"))
    implementation(project(":feature:common"))
    implementation(libs.image.loading)
}
