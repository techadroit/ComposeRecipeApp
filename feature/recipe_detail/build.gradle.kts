plugins {
    id("android.module.library")
    id("android.library.compose")
    id("android.application.hilt")
}
android {
    namespace = "com.feature.recipe.detail"
}

dependencies {

    implementation(project(":archerviewmodel"))
    implementation(project(":domain:common"))
    implementation(project(":domain:recipe"))
    implementation(project(":domain:favourite"))
    implementation(project(":app-navigation"))
    implementation(project(":core:navigation"))
    implementation(project(":core:platform"))
    implementation(project(":core:themes"))
    implementation(project(":feature:common"))
    implementation(project(":feature:common-ui"))
    implementation("com.google.android.exoplayer:exoplayer:2.14.1")
    implementation(libs.image.loading)
}
