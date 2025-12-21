package com.khaki.smartrss.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingViewModel(
    private val useCase: SettingUseCase
) : ViewModel() {

    val uiState: StateFlow<SettingUiState> = useCase.setting.map {
        SettingUiState(
            isSummaryEnabled = it.isDetailSummary
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SettingUiState()
    )

    fun toggleSummaryEnabled(isEnabled: Boolean) {
        viewModelScope.launch {
            useCase.updateSummarySetting(isEnabled)
        }
    }
}
