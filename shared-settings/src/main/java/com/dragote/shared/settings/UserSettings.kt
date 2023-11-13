package com.dragote.shared.settings

import kotlinx.coroutines.flow.StateFlow

enum class AppTheme {
    LIGHT,
    DARK,
    SYSTEM;

    companion object {
        fun fromOrdinal(ordinal: Int) = values()[ordinal]
    }
}

interface UserSettings {
    val themeStream: StateFlow<AppTheme>
    fun setAppTheme(theme: AppTheme)
}