package com.github.ntngel1.linkedout.proxy_settings.data

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.github.ntngel1.linkedout.proxy_settings.domain.repository.ProxySettingsRepository
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxySettingsEntity
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SharedProxySettingsRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
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