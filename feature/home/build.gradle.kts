plugins {
    id("android.module.library")
    id("android.library.compose")
    id("android.application.hilt")
}
android {
    namespace = "com.feature.recipe.home"
}

dependencies {

    implementation(project(":archerviewmodel"))
    implementation(project(":domain:common"))
    implementation(project(":domain:recipe"))
    implementation(project(":app-navigation"))
    implementation(project(":core:navigation"))
    implementation(project(":core:platform"))
    implementation(project(":feature:common"))
    implementation(project(":feature:common-ui"))
    implementation(libs.androidx.compose.constraintLayout)
    implementation(libs.image.loading)
}
