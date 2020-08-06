package com.github.ntngel1.linkedout.proxy_settings.presentation

import com.github.ntngel1.linkedout.proxy_settings.entity.ConnectionQualityEntity
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyType

data class ProxySettingsState(
    val isSaveButtonVisible: Boolean = false,
    val isCheckConnectionQualityButtonVisible: Boolean = false,
    val isCheckingConnectionQuality: Boolean = false,
    val isProxyInputsVisible: Boolean = false,
    val hostnameCursorPosition: Int = 0,
    val portCursorPosition: Int = 0,
    val connectionLatencyMs: Int? = null,
    val connectionStability: ConnectionQualityEntity.Stability? = null,

    // Already saved proxy configuration
    val savedProxyType: ProxyType? = null,
    val savedProxyHostname: String? = null,
    val savedProxyPort: Int? = null,

    // Fields that user are editing
    val newProxyType: ProxyType? = savedProxyType,
    val newProxyHostname: String? = savedProxyHostname,
    val newProxyPort: Int? = savedProxyPort
)