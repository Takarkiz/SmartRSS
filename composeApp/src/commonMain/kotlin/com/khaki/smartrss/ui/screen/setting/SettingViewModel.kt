package com.khaki.smartrss.ui.screen.setting

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    fun toggleSummaryEnabled(isEnabled: Boolean) {
        _uiState.update {
            it.copy(
                isSummaryEnabled = isEnabled
            )
        }
    }
}
