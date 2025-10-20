package com.khaki.smartrss.ui.screen.rss

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaki.modules.core.model.feed.FormType
import com.khaki.modules.core.model.feed.Popular
import com.khaki.modules.core.model.feed.RssCategory
import com.khaki.modules.core.model.feed.URL
import com.khaki.modules.core.model.feed.UserId
import com.khaki.smartrss.ui.Result
import com.khaki.smartrss.ui.screen.rss.model.RegisterableRssGroup
import com.khaki.smartrss.ui.screen.rss.model.RegisteredRssGroup
import com.khaki.smartrss.ui.screen.rss.model.RssInputFormType
import com.khaki.smartrss.ui.screen.rss.usecase.RssAppendingError
import com.khaki.smartrss.ui.screen.rss.usecase.RssUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val errorMessage: MutableSharedFlow<String> = MutableSharedFlow()
    val errorMessageFlow: SharedFlow<String> = errorMessage.asSharedFlow()

    fun appendRssFeed(group: RegisterableRssGroup, form: FormType) {

        viewModelScope.launch {

            viewModelState.update {
                it.copy(
                    isLoadingGetRss = true
                )
            }

            val result = when (group) {
                RegisterableRssGroup.Qiita -> {
                    rssUseCase.checkAndAddQiitaRssFeed(form)
                }

                RegisterableRssGroup.Zenn -> {
                    rssUseCase.checkAndAddZennRssFeed(form)
                }

                RegisterableRssGroup.HatenaBlog -> {
                    if (form is UserId) {
                        rssUseCase.checkAndAddHatenaRssFeed(form)
                    } else {
                        Result.Error(RssAppendingError.IllegalInputFormat)
                    }
                }

                RegisterableRssGroup.Github -> {
                    rssUseCase.checkAndAddGithubRssFeed()
                }

                RegisterableRssGroup.Others -> {
                    if (form is URL) {
                        rssUseCase.checkAndAddOtherRssFeed(form)
                    } else {
                        Result.Error(RssAppendingError.IllegalInputFormat)
                    }
                }
            }

            viewModelState.update {
                it.copy(
                    isLoadingGetRss = false,
                    expandedBottomSheet = null
                )
            }

            when (result) {
                is Result.Success -> {
                    // Nothing to do
                }

                is Result.Error -> {
                    when (result.error) {
                        RssAppendingError.DuplicateRssCategory -> {
                            errorMessage.emit("既に登録されているRSSフィードです")
                        }

                        is RssAppendingError.FetchingFailed -> {
                            errorMessage.emit("RSSフィードの取得に失敗しました")
                        }

                        RssAppendingError.IllegalInputFormat -> {
                            errorMessage.emit("入力形式が不正です")
                        }

                        RssAppendingError.NotFoundFeed -> {
                            errorMessage.emit("フィードが見つかりませんでした")
                        }

                        RssAppendingError.NotImplemented -> {
                            errorMessage.emit("未実装です")
                        }

                        is RssAppendingError.RoomDatabaseError -> {
                            errorMessage.emit("Roomデータベースのエラーが発生しました")
                        }
                    }
                }
            }
        }
    }

    fun updateExpandedBottomSheet(group: RegisterableRssGroup?) {
        viewModelState.update {
            it.copy(
                expandedBottomSheet = group
            )
        }
    }

    // すでに登録済みのRSSの最新フィードを取得する
    fun refreshFeeds() {
        viewModelScope.launch {
            rssUseCase.refreshFeeds()
        }
    }

}

internal data class RssViewModelState(
    val isLoadingGetRss: Boolean = false,
    val expandedBottomSheet: RegisterableRssGroup? = null,
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
            registeredRssGroupList = registeredMap,
            expandedBottomSheet = expandedBottomSheet,
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
