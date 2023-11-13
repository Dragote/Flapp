plugins {
    `android-library`
    `kotlin-android`
    id("com.google.devtools.ksp")
}

apply<plugins.BaseModulePlugin>()
apply<plugins.ComposeModulePlugin>()

android {
    namespace = "com.dragote.shared.ui"
}

dependencies {
    //Modules
    implementation(project(":shared-country"))
    implementation(project(":shared-stats"))
    implementation(project(":shared-settings"))
}