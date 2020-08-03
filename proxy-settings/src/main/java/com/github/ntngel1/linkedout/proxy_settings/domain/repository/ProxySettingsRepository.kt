package com.github.ntngel1.linkedout.proxy_settings.domain.repository

import com.github.ntngel1.linkedout.proxy_settings.entity.ProxySettingsEntity

interface ProxySettingsRepository {
    suspend fun get(): ProxySettingsEntity?
    suspend fun save(proxySettings: ProxySettingsEntity?)
}