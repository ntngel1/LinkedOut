package com.github.ntngel1.linkedout.proxy_settings.entity

data class ProxySettingsEntity(
    val proxyType: ProxyType,
    val proxyHostname: String?,
    val proxyPort: Int?
)