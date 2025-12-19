package com.khaki.smartrss

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.serialization.NavBackStackSerializer
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.khaki.smartrss.components.MainScreen
import com.khaki.smartrss.platform.ExternalBrowserLauncher
import com.khaki.smartrss.ui.navigation.Home
import com.khaki.smartrss.ui.navigation.RssFeed
import com.khaki.smartrss.ui.navigation.Screen
import com.khaki.smartrss.ui.navigation.Setting
import com.khaki.smartrss.ui.screen.rssfeed.RSSFeedScreen
import com.khaki.smartrss.ui.screen.setting.SettingScreen
import com.khaki.smartrss.ui.screen.setting.SettingViewModel
import com.khaki.smartrss.ui.theme.SmartRssTheme
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    SmartRssTheme {

        val configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(Screen::class) {
                    subclass(Home.serializer())
                    subclass(Setting.serializer())
                    subclass(RssFeed.serializer())
                }
            }
        }

        val backStack = rememberNavBackStack<Screen>(
            configuration = configuration,
            Home,
        )

        var externalBrowserUrl: String? by remember { mutableStateOf(null) }

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLast() },
            entryProvider = entryProvider {
                entry<Home> { _ ->
                    MainScreen(
                        onFeedClick = { _, url ->
                            backStack.add(
                                RssFeed(
                                    title = "詳細",
                                    url = url,
                                )
                            )
                        },
                        onSettingClick = {
                            backStack.add(Setting)
                        }
                    )
                }

                entry<Setting> { _ ->
                    val settingViewModel = koinInject<SettingViewModel>()
                    val uiState by settingViewModel.uiState.collectAsState()
                    SettingScreen(
                        uiState = uiState,
                        onClickSummaryEnable = { isEnabled ->
                            settingViewModel.toggleSummaryEnabled(isEnabled)
                        },
                        onBack = { backStack.removeLast() }
                    )
                }

                entry<RssFeed> { result ->
                    RSSFeedScreen(
                        title = result.title,
                        url = result.url,
                        onBack = { backStack.removeLast() },
                        onRequestUrlChange = { url ->
                            externalBrowserUrl = url
                        }
                    )
                }
            }
        )

        ExternalBrowserLauncher(
            url = externalBrowserUrl,
            onLaunched = { externalBrowserUrl = null }
        )
    }
}

@Composable
inline fun <reified T : NavKey> rememberNavBackStack(
    configuration: SavedStateConfiguration,
    vararg elements: T,
): NavBackStack<T> {
    return rememberSerializable(
        configuration = configuration,
        serializer = NavBackStackSerializer(PolymorphicSerializer(T::class)),
    ) {
        NavBackStack(*elements)
    }
}
