package com.khaki.smartrss.ui.screen.rss

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaki.modules.core.model.feed.FormType
import com.khaki.smartrss.ui.screen.rss.model.RegisterableRssGroup
import com.khaki.smartrss.ui.screen.rss.model.RegisteredRssGroup
import com.khaki.smartrss.ui.screen.rss.model.RssInputFormType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RssViewModel(
    private val rssUseCase: RssUseCase
) : ViewModel() {

    private val viewModelState: MutableStateFlow<RssViewModelState> =
        MutableStateFlow(RssViewModelState())

    val uiState: StateFlow<RssUiState> = viewModelState
        .map {
            it.toUiState()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = RssViewModelState().toUiState()
        )

    fun appendRssFeed(group: RegisterableRssGroup, form: FormType) {

        viewModelScope.launch {
            when (group) {
                RegisterableRssGroup.Qiita -> {
                    rssUseCase.chackAndAddQiitaRssFeed(form)
                }

                RegisterableRssGroup.Zenn -> {
                    // Zenn用のRSSフィード追加ロジックをここに実装
                }

                RegisterableRssGroup.HatenaBlog -> {
                    // はてなブログ用のRSSフィード追加ロジックをここに実装
                }

                RegisterableRssGroup.Github -> {
                    // GitHubリリースノート用のRSSフィード追加ロジックをここに実装
                }

                RegisterableRssGroup.Others -> {
                    // カスタムRSS用のRSSフィード追加ロジックをここに実装
                }
            }
        }
    }
}

private data class RssViewModelState(
    val isLoadingGetRss: Boolean = false,
    val registerableRssFormat: Map<RegisterableRssGroup, List<RssInputFormType>> = emptyMap(),
    val registeredRssGroupList: Map<RegisterableRssGroup, List<RegisteredRssGroup>> = emptyMap()
) {

    fun toUiState() = RssUiState(
        isLoading = isLoadingGetRss,
        registerableRssFormat = registerableRssFormat,
        registeredRssGroupList = registeredRssGroupList
    )
}
