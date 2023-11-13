package com.dragote.feature.settings.presentation

import com.dragote.component.test.coroutines.TestCoroutineExtension
import com.dragote.feature.settings.domain.ClearProgressUseCase
import com.dragote.shared.settings.AppTheme
import com.dragote.shared.settings.UserSettings
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExtendWith(
    TestCoroutineExtension::class,
)
class SettingsViewModelTest {

    private val clearProgressUseCase: ClearProgressUseCase = mock()
    private val userSettings: UserSettings = mock()

    private val viewModel = SettingsViewModel(
        clearProgressUseCase,
        userSettings,
    )

    @Test
    fun `clear progress EXPECT clear progress`() = runTest {
        viewModel.clearProgress()

        verify(clearProgressUseCase).invoke()
    }

    @Test
    fun `change color theme EXPECT set app theme`() = runTest {
        val theme = AppTheme.DARK
        viewModel.changeColorTheme(theme)

        verify(userSettings).setAppTheme(theme)
    }
}