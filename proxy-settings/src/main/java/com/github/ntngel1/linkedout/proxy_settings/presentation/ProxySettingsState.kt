package com.github.ntngel1.linkedout.proxy_settings.presentation

import com.github.ntngel1.linkedout.proxy_settings.entity.ConnectionQualityEntity
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxySettingsEntity

data class ProxySettingsState(
    val isSaveButtonVisible: Boolean = false,
    val isCheckConnectionQualityButtonVisible: Boolean = false,
    val isCheckingConnectionQuality: Boolean = false,
    val isProxyInputsVisible: Boolean = false,
    val savedProxySettings: ProxySettingsEntity? = null,
    val newProxySettings: ProxySettingsEntity? = savedProxySettings,
    val hostnameCursorPosition: Int = 0,
    val portCursorPosition: Int = 0,
    val connectionQuality: ConnectionQualityEntity? = null
)