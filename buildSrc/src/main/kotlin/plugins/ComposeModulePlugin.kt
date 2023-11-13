package plugins

import Versions
import android
import compose
import org.gradle.api.Plugin
import org.gradle.api.Project

class ComposeModulePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        setProjectConfig(project)
    }

    private fun setProjectConfig(project: Project) {
        project.android().apply {
            buildFeatures {
                compose = true
            }
            composeOptions {
                kotlinCompilerExtensionVersion = Versions.compose
            }

        }
        project.dependencies.apply {
            compose()
        }
    }
}