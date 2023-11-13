plugins {
    `android-library`
    `kotlin-android`
    id("com.google.devtools.ksp")
}

apply<plugins.BaseModulePlugin>()
apply<plugins.ComposeModulePlugin>()

android {
    namespace = "com.dragote.feature.progress"

    ksp {
        arg("compose-destinations.mode", "destinations")
        arg("compose-destinations.moduleName", "progress")
    }
}

dependencies {
    coil()
    composeNavigation()
    coroutines()
    hilt()
    testUtils()

    implementation(project(":component-test-coroutines"))

    implementation(project(":shared-country"))
    implementation(project(":shared-stats"))
    implementation(project(":shared-ui"))
}