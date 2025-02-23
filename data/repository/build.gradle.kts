
plugins {
    id("android.module.library")
    id("android.application.hilt")
    id("android.application.network")
    id("android.application.database")
    id("androidx.room")
    alias(libs.plugins.ksp.gradlePlugin)
}

android {
    namespace = "com.data.repository"
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.datastore)
    implementation(project(":core:platform"))
    implementation(project(":core:network"))
    ksp(libs.android.room.compiler)
}
