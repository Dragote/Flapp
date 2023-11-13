package com.dragote.feature.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dragote.feature.settings.R
import com.dragote.feature.settings.presentation.SettingsViewModel
import com.dragote.shared.settings.AppTheme
import com.dragote.shared.ui.components.AlertDialog
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    var openAlertDialog by remember { mutableStateOf(false) }
    val theme = viewModel.theme.collectAsState()

    if (openAlertDialog) {
        AlertDialog(
            onDismiss = { openAlertDialog = false },
            onConfirm = {
                openAlertDialog = false
                viewModel.clearProgress()
            },
            title = stringResource(id = R.string.alert_title),
            text = stringResource(id = R.string.alert_text),
            confirmText = stringResource(id = R.string.alert_confirm),
            dismissText = stringResource(id = R.string.alert_dismiss),
            icon = Icons.Default.Info
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,
    ) {
        Button(
            onClick = { openAlertDialog = true },
        ) {
            Text(text = stringResource(id = R.string.clear_button))
        }

        Spacer(modifier = Modifier.height(16.dp))

        ThemeSwitcher(
            selectedTheme = theme.value,
            onItemSelected = viewModel::changeColorTheme,
        )
    }
}

@Composable
fun ThemeSwitcher(
    selectedTheme: AppTheme,
    onItemSelected: (AppTheme) -> Unit,
    modifier: Modifier = Modifier,
) {
    val themeItems = listOf(
        RadioButtonItem(
            id = AppTheme.LIGHT.ordinal,
            title = stringResource(id = R.string.light_theme),
        ),
        RadioButtonItem(
            id = AppTheme.DARK.ordinal,
            title = stringResource(id = R.string.dark_theme),
        ),
        RadioButtonItem(
            id = AppTheme.SYSTEM.ordinal,
            title = stringResource(id = R.string.system_theme),
        ),
    )

    Column(
        modifier = modifier.padding(all = 16.dp),
        horizontalAlignment = CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.choose_theme_title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 24.dp),
        )
        Spacer(modifier = Modifier.height(24.dp))
        RadioGroup(
            items = themeItems,
            selected = selectedTheme.ordinal,
            onItemSelect = { id -> onItemSelected(AppTheme.fromOrdinal(id)) },
            modifier = Modifier.fillMaxWidth(.6f),
        )
    }
}

data class RadioButtonItem(
    val id: Int,
    val title: String,
)

@Composable
private fun RadioGroupItem(
    item: RadioButtonItem,
    selected: Boolean,
    onClick: ((Int) -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .selectable(
                selected = selected,
                onClick = { onClick?.invoke(item.id) },
                role = Role.RadioButton
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = item.title,
        )
    }
}

@Composable
fun RadioGroup(
    items: Iterable<RadioButtonItem>,
    selected: Int,
    onItemSelect: ((Int) -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.selectableGroup()
    ) {
        items.forEach { item ->
            RadioGroupItem(
                item = item,
                selected = selected == item.id,
                onClick = { onItemSelect?.invoke(item.id) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}