package com.dragote.shared.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class UserSettingsImpl @Inject constructor(
    @ApplicationContext context: Context
) : UserSettings {

    override val themeStream: MutableStateFlow<AppTheme>
    private var theme: AppTheme by AppThemePreferenceDelegate("app_theme", AppTheme.SYSTEM)

    private val preferences: SharedPreferences =
        context.getSharedPreferences("sample_theme", Context.MODE_PRIVATE)

    init {
        themeStream = MutableStateFlow(theme)
    }

    override fun setAppTheme(theme: AppTheme) {
        this.theme = theme
    }

    inner class AppThemePreferenceDelegate(
        private val name: String,
        private val default: AppTheme,
    ) : ReadWriteProperty<Any?, AppTheme> {

        override fun getValue(thisRef: Any?, property: KProperty<*>): AppTheme =
            AppTheme.fromOrdinal(preferences.getInt(name, default.ordinal))

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: AppTheme) {
            themeStream.value = value
            preferences.edit {
                putInt(name, value.ordinal)
            }
        }
    }
}