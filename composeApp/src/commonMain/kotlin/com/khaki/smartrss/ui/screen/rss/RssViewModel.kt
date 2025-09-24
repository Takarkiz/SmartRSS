package com.khaki.smartrss.ui.screen.rss

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaki.modules.core.model.feed.FormType
import com.khaki.modules.core.model.feed.RssCategory
import com.khaki.smartrss.ui.screen.rss.model.RegisterableRssGroup
import com.khaki.smartrss.ui.screen.rss.model.RegisteredRssGroup
import com.khaki.smartrss.ui.screen.rss.model.RssInputFormType
import com.khaki.smartrss.ui.screen.rss.usecase.RssUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RssViewModel(
    private val rssUseCase: RssUseCase
) : ViewModel() {

    private val viewModelState: MutableStateFlow<RssViewModelState> =
        MutableStateFlow(RssViewModelState())

    val uiState: StateFlow<RssUiState> =
        combine(viewModelState, rssUseCase.followingCategories) { viewModelState, categories ->
            viewModelState.toUiState(categories)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = RssViewModelState().toUiState(emptyList())
        )

    init {
        viewModelState.update { viewModelState ->
            viewModelState.copy(
                registerableRssFormat = mapOf(
                    RegisterableRssGroup.Qiita to listOf(
                        RssInputFormType.USER,
                        RssInputFormType.TAG,
                        RssInputFormType.POPULAR
                    ),
                    RegisterableRssGroup.Zenn to listOf(
                        RssInputFormType.USER,
                        RssInputFormType.TAG,
                        RssInputFormType.POPULAR
                    ),
                    RegisterableRssGroup.HatenaBlog to listOf(
                        RssInputFormType.USER
                    ),
                    RegisterableRssGroup.Github to listOf(
                        RssInputFormType.USER,
                    ),
                    RegisterableRssGroup.Others to listOf(
                        RssInputFormType.URL
                    )
                )
            )
        }
    }

    fun appendRssFeed(group: RegisterableRssGroup, form: FormType) {

        viewModelScope.launch {
            when (group) {
                RegisterableRssGroup.Qiita -> {
                    rssUseCase.checkAndAddQiitaRssFeed(form)
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

    fun toUiState(
        rssCategoryList: List<RssCategory>
    ) = RssUiState(
        isLoading = isLoadingGetRss,
        registerableRssFormat = registerableRssFormat,
        registeredRssGroupList = rssCategoryList
            .mapNotNull {
                val group = when (it.type) {
                    RssCategory.RSSGroupType.Qiita -> RegisterableRssGroup.Qiita
                    RssCategory.RSSGroupType.Zenn -> RegisterableRssGroup.Zenn
                    RssCategory.RSSGroupType.HatenaBlog -> RegisterableRssGroup.HatenaBlog
                    RssCategory.RSSGroupType.Github -> RegisterableRssGroup.Github
                    RssCategory.RSSGroupType.Others -> RegisterableRssGroup.Others
                }
                RegisteredRssGroup(
                    id = it.id,
                    name = it.name,
                    url = it.url,
                    type = group,
                )
            }
            .groupBy { it.type }
    )
}
