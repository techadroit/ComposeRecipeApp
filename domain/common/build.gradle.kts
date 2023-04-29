plugins {
    id("android.module.library")
}

android {
    namespace = "com.domain.common"
}

dependencies {
    implementation(project(":data:repository"))
}
