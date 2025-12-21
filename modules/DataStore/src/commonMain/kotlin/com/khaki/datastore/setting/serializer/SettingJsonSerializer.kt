package com.khaki.datastore.setting.serializer

import androidx.datastore.core.okio.OkioSerializer
import com.khaki.datastore.setting.model.Setting
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource

val json = Json { ignoreUnknownKeys = true }

internal object SettingJsonSerializer : OkioSerializer<Setting> {
    override val defaultValue: Setting = Setting.default()

    override suspend fun readFrom(source: BufferedSource): Setting =
        json.decodeFromString<Setting>(source.readUtf8())

    override suspend fun writeTo(
        t: Setting,
        sink: BufferedSink
    ) {
        sink.writeUtf8(json.encodeToString(Setting.serializer(), t))
    }

}
