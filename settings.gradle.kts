pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Flapp"
include(":app")
include(":component-test-coroutines")
include(":feature-progress")
include(":feature-train")
include(":feature-settings")
include(":shared-country")
include(":shared-ui")
include(":shared-settings")
include(":shared-stats")