package com.github.ntngel1.linkedout.proxy_settings.data

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.github.ntngel1.linkedout.proxy_settings.domain.repository.ProxySettingsRepository
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxySettingsEntity
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi
import javax.inject.Inject

class ProxySettingsRepositoryImp @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
    private val telegramClient: Client
) : ProxySettingsRepository {

    private var cachedProxySettings: ProxySettingsEntity?

    init {
        cachedProxySettings = sharedPreferences.getString(PROXY_SETTINGS_KEY, null)
            ?.let { json ->
                gson.fromJson(json, ProxySettingsEntity::class.java)
            }
    }

    override suspend fun get(): ProxySettingsEntity? {
        return cachedProxySettings
    }

    @SuppressLint("ApplySharedPref")
    override suspend fun save(proxySettings: ProxySettingsEntity?) {
        //telegramClient.send(TdApi.AddProxy())

        val json = proxySettings?.let(gson::toJson)
        sharedPreferences.edit()
            .putString(PROXY_SETTINGS_KEY, json)
            .commit()

        cachedProxySettings = proxySettings
    }

    companion object {
        private const val PROXY_SETTINGS_KEY = "PROXY_SETTINGS"
    }
}