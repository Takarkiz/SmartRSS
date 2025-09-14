package com.khaki.smartrss

import androidx.compose.runtime.Composable
import com.khaki.smartrss.components.MainScreen
import com.khaki.smartrss.ui.theme.SmartRssTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    SmartRssTheme {
        MainScreen()
    }
}