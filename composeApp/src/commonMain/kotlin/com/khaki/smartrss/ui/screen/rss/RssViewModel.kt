package com.khaki.smartrss.ui.screen.rss

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaki.modules.core.model.feed.FormType
import com.khaki.modules.core.model.feed.Popular
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

internal data class RssViewModelState(
    val isLoadingGetRss: Boolean = false
) {

    fun toUiState(
        rssCategoryList: List<RssCategory>
    ): RssUiState {
        val registeredMap: Map<RegisterableRssGroup, List<RegisteredRssGroup>> =
            rssCategoryList
                .map {
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

        val popularRegisteredGroupTypes = rssCategoryList
            .filter { it.formType is Popular }
            .map { it.type }
            .toSet()

        val registerable = defaultAllRegisterableRss.mapValues { (group, forms) ->
            val groupType = when (group) {
                RegisterableRssGroup.Qiita -> RssCategory.RSSGroupType.Qiita
                RegisterableRssGroup.Zenn -> RssCategory.RSSGroupType.Zenn
                else -> null
            }
            if (groupType in popularRegisteredGroupTypes) {
                forms.filter { it != RssInputFormType.POPULAR }
            } else {
                forms
            }
        }

        return RssUiState(
            isLoading = isLoadingGetRss,
            registerableRssFormat = registerable,
            registeredRssGroupList = registeredMap
        )
    }

    private val defaultAllRegisterableRss: Map<RegisterableRssGroup, List<RssInputFormType>> =
        mapOf(
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
}
