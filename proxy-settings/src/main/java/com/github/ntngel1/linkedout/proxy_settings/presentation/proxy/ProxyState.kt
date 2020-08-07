package com.github.ntngel1.linkedout.proxy_settings.presentation.proxy

import com.github.ntngel1.linkedout.proxy_settings.entity.ConnectionQualityEntity
import com.github.ntngel1.linkedout.proxy_settings.presentation.proxy.enums.ProxyType

// TODO: Maybe we can have just only cursorPosition field that can be used across all inputs?
data class ProxyState(
    val proxyId: Int = -1,
    /** [isUsernameAndPasswordInputsVisible] is true if [ProxyType] == [ProxyType.HTTP] || [ProxyType.SOCKS5] */
    val isUsernameAndPasswordInputsVisible: Boolean = false,
    /** [isSecretInputVisible] is true if [ProxyType] == [ProxyType.MT_PROTO] */
    val isSecretInputVisible: Boolean = false,
    val isPingProxyButtonVisible: Boolean = false,
    val isSaveButtonVisible: Boolean = false,
    val isPingingProxy: Boolean = false,
    val connectionLatencyMs: Int? = null,
    val connectionStability: ConnectionQualityEntity.Stability? = null,

    // Already saved proxy configuration
    val savedProxyType: ProxyType? = null,
    val savedProxyHostname: String? = null,
    val savedProxyPort: Int? = null,
    /** [savedProxyUsername] is for [ProxyType.HTTP] and [ProxyType.SOCKS5] */
    val savedProxyUsername: String? = null,
    /** [savedProxyPassword] is for [ProxyType.HTTP] and [ProxyType.SOCKS5] */
    val savedProxyPassword: String? = null,
    /** [savedProxySecret] is for [ProxyType.MT_PROTO] */
    val savedProxySecret: String? = null,

    // Fields that user is editing
    val newProxyType: ProxyType? = savedProxyType,
    val newProxyHostname: String? = savedProxyHostname,
    val newProxyPort: Int? = savedProxyPort,
    /** [newProxyUsername] is for [ProxyType.HTTP] and [ProxyType.SOCKS5] */
    val newProxyUsername: String? = savedProxyUsername,
    /** [newProxyPassword] is for [ProxyType.HTTP] and [ProxyType.SOCKS5] */
    val newProxyPassword: String? = savedProxyPassword,
    /** [newProxySecret] is for [ProxyType.MT_PROTO] */
    val newProxySecret: String? = savedProxySecret,

    // Cursor positions for inputs that user is editing. We render our screen every time user enters
    // new character in any input, and it causes text inputs reset their states and cursor jumps to
    // beginning of text input.
    val hostnameCursorPosition: Int = 0,
    val portCursorPosition: Int = 0,
    val usernameCursorPosition: Int = 0,
    val passwordCursorPosition: Int = 0,
    val secretCursorPosition: Int = 0
)