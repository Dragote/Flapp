plugins {
    `android-library`
    `kotlin-android`
    id("com.google.devtools.ksp")
}

apply<plugins.BaseModulePlugin>()
apply<plugins.ComposeModulePlugin>()

android {
    namespace = "com.dragote.feature.train"

    ksp {
        arg("compose-destinations.mode", "destinations")
        arg("compose-destinations.moduleName", "train")
    }
}

dependencies {
    coil()
    composeNavigation()
    coroutines()
    hilt()
    testUtils()

    //Modules
    implementation(project(":component-test-coroutines"))

    implementation(project(":shared-country"))
    implementation(project(":shared-settings"))
    implementation(project(":shared-stats"))
    implementation(project(":shared-ui"))
}