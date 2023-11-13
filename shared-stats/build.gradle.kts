plugins {
    `android-library`
    `kotlin-android`
    id("com.google.devtools.ksp")
}

apply<plugins.BaseModulePlugin>()
apply<plugins.ComposeModulePlugin>()

android {
    namespace = "com.dragote.shared.stats"
}

dependencies {
    coroutines()
    hilt()
    room()
    testUtils()

    //Modules
    implementation(project(":shared-country"))
}