plugins {
    `android-library`
    `kotlin-android`
    id("com.google.devtools.ksp")
}

apply<plugins.BaseModulePlugin>()
apply<plugins.ComposeModulePlugin>()

android {
    namespace = "com.dragote.feature.settings"

    ksp {
        arg("compose-destinations.mode", "destinations")
        arg("compose-destinations.moduleName", "settings")
    }
}

dependencies {
    composeNavigation()
    coroutines()
    hilt()
    testUtils()

    implementation(project(":component-test-coroutines"))

    implementation(project(":shared-settings"))
    implementation(project(":shared-stats"))
    implementation(project(":shared-ui"))
}