package com.khaki.smartrss.ui.screen.rss

import com.khaki.smartrss.ui.screen.rss.model.RegisterableRssGroup
import com.khaki.smartrss.ui.screen.rss.model.RegisteredRssGroup
import com.khaki.smartrss.ui.screen.rss.model.RegisteredRssGroupPreviewParameterProvider
import com.khaki.smartrss.ui.screen.rss.model.RssInputFormType
import com.khaki.smartrss.ui.screen.rss.model.RssInputFormTypePreviewParameterProvider
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

data class RssUiState(
    val isLoading: Boolean,
    val registerableRssFormat: Map<RegisterableRssGroup, List<RssInputFormType>>,
    val registeredRssGroupList: Map<RegisterableRssGroup, List<RegisteredRssGroup>>
)

internal class RssUiStatePreviewParameterProvider : PreviewParameterProvider<RssUiState> {
    override val values: Sequence<RssUiState>
        get() {
            val registeredProvider = RegisteredRssGroupPreviewParameterProvider()
            val registeredList = registeredProvider.values.toList()
            val registeredMap: Map<RegisterableRssGroup, List<RegisteredRssGroup>> =
                registeredList.groupBy { it.type }

            val formTypes = RssInputFormTypePreviewParameterProvider().values.toList()
            val formMap: Map<RegisterableRssGroup, List<RssInputFormType>> =
                RegisterableRssGroup.entries.associateWith { formTypes }

            return sequenceOf(
                RssUiState(
                    isLoading = false,
                    registerableRssFormat = formMap,
                    registeredRssGroupList = registeredMap
                ),
                RssUiState(
                    isLoading = true,
                    registerableRssFormat = formMap,
                    registeredRssGroupList = emptyMap()
                )
            )
        }
}
