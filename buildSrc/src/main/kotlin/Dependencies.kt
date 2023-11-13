import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
    const val composeUI = "androidx.compose.ui:ui"
    const val composeGraphics = "androidx.compose.ui:ui-graphics"
    const val composeToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val material3 = "androidx.compose.material3:material3:${Versions.material3}"
    const val materialIcons = "androidx.compose.material:material-icons-extended"
    const val uiTooling = "androidx.compose.ui:ui-tooling"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest"

    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val navigationCompose =
        "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
    const val composeDestinationsCore =
        "io.github.raamcosta.compose-destinations:animations-core:${Versions.destinations}"
    const val composeDestinationsKsp =
        "io.github.raamcosta.compose-destinations:ksp:${Versions.destinations}"

    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val turbine =
        "app.cash.turbine:turbine:${Versions.turbine}"

    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltNavigationCompose =
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    const val coil = "io.coil-kt:coil-compose:${Versions.coil}"
    const val coilSvg = "io.coil-kt:coil-svg:${Versions.coilSvg}"

    const val junitExt = "androidx.test.ext:junit${Versions.junitExt}"
    const val junitJupiter = "org.junit.jupiter:junit-jupiter:${Versions.junit5}"
    const val junitApi = "org.junit.jupiter:junit-jupiter-api:${Versions.junit5}"
    const val junitParams = "org.junit.jupiter:junit-jupiter-params:${Versions.junit5}"
    const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit5}"

    const val mockito = "org.mockito.kotlin:mockito-kotlin:${Versions.mockito}"
}

fun DependencyHandler.compose() {
    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.composeUI)
    implementation(Dependencies.composeToolingPreview)
    implementation(Dependencies.composeGraphics)
    implementation(Dependencies.material3)
    implementation(Dependencies.materialIcons)
    androidTestImplementation(Dependencies.composeBom)
    debugImplementation(Dependencies.uiTooling)
    debugImplementation(Dependencies.uiTestManifest)
}

fun DependencyHandler.composeNavigation() {
    implementation(Dependencies.navigationCompose)
    implementation(Dependencies.composeDestinationsCore)
    ksp(Dependencies.composeDestinationsKsp)
}

fun DependencyHandler.coil() {
    implementation(Dependencies.coil)
    implementation(Dependencies.coilSvg)
}

fun DependencyHandler.coroutines() {
    implementation(Dependencies.coroutines)
    implementation(Dependencies.coroutinesTest)
    testImplementation(Dependencies.coroutinesTest)
    testImplementation(Dependencies.turbine)
}

fun DependencyHandler.room() {
    implementation(Dependencies.roomRuntime)
    implementation(Dependencies.roomKtx)
    ksp(Dependencies.roomCompiler)
}

fun DependencyHandler.hilt() {
    implementation(Dependencies.hiltAndroid)
    implementation(Dependencies.hiltNavigationCompose)
    ksp(Dependencies.hiltCompiler)
}

fun DependencyHandler.testUtils() {
    androidTestImplementation(Dependencies.junitExt)
    implementation(Dependencies.junitJupiter)
    testImplementation(Dependencies.junitApi)
    testImplementation(Dependencies.junitParams)
    testRuntimeOnly(Dependencies.junitEngine)
    testImplementation(Dependencies.mockito)
}