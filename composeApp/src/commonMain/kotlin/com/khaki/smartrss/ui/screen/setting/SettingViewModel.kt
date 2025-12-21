package com.khaki.smartrss.ui.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingViewModel(
    private val useCase: SettingUseCase,
    private val deleteAllFeedsUseCase: DeleteAllFeedsUseCase,
) : ViewModel() {

    private val _showDeleteAllFeedsDialog = MutableStateFlow(false)

    val uiState: StateFlow<SettingUiState> = combine(
        useCase.setting,
        _showDeleteAllFeedsDialog
    ) { setting, showDeleteAllFeedsDialog ->
        SettingUiState(
            isSummaryEnabled = setting.isDetailSummary,
            showDeleteAllFeedsDialog = showDeleteAllFeedsDialog
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

    fun showDeleteAllFeedsDialog() {
        _showDeleteAllFeedsDialog.value = true
    }

    fun hideDeleteAllFeedsDialog() {
        _showDeleteAllFeedsDialog.value = false
    }

    fun deleteAllFeeds() {
        viewModelScope.launch {
            deleteAllFeedsUseCase()
            hideDeleteAllFeedsDialog()
        }
    }
}
