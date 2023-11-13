package com.dragote.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dragote.feature.settings.domain.ClearProgressUseCase
import com.dragote.shared.settings.AppTheme
import com.dragote.shared.settings.UserSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val clearProgressUseCase: ClearProgressUseCase,
    private val userSettings: UserSettings,
) : ViewModel() {

    val theme = userSettings.themeStream

    fun clearProgress() {
        viewModelScope.launch {
            clearProgressUseCase()
        }
    }

    fun changeColorTheme(theme: AppTheme) {
        userSettings.setAppTheme(theme)
    }
}