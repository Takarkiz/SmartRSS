package com.khaki.datastore.setting.model

import kotlinx.serialization.Serializable

@Serializable
data class Setting(
    val shouldShowDetailSummary: Boolean,
) {

    companion object {
        fun default() = Setting(
            shouldShowDetailSummary = false,
        )
    }
}

