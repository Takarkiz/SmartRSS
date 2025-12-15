package com.khaki.smartrss.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.KSerializer

interface Screen : NavKey

@Serializable
data object Home : Screen

@Serializable
data class RssFeed(
    val title: String,
    val url: String,
) : Screen
