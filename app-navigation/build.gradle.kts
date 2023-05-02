plugins {
    id("android.module.library")
}

android {
    namespace = "com.recipe.app.navigation"
}

kapt {
    generateStubs = true
    correctErrorTypes = true
}

dependencies {
    implementation(libs.androidx.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(project(":core:navigation"))
}
