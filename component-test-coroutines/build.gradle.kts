plugins {
    `android-library`
    `kotlin-android`
    id("com.google.devtools.ksp")
}

apply<plugins.BaseModulePlugin>()

android {
    namespace = "com.dragote.component.test.coroutines"
}

dependencies {
    testUtils()
    coroutines()
}