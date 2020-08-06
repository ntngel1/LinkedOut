package com.github.ntngel1.linkedout.proxy_settings

import com.github.ntngel1.linkedout.proxy_settings.domain.usecase.get_proxy_settings.GetProxySettingsUseCase
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxySettingsEntity
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyType
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.net.*
import javax.inject.Inject

class SettingsBasedProxySelector @Inject constructor(
    private val getProxySettings: GetProxySettingsUseCase
) : ProxySelector() {

    override fun connectFailed(uri: URI?, sa: SocketAddress?, ioe: IOException?) {}

    override fun select(uri: URI?): List<Proxy> {
        val proxySettings = runBlocking { getProxySettings() }
        return listOf(proxySettings.toJavaNetProxy())
    }

    private fun ProxySettingsEntity.toJavaNetProxy(): Proxy {
        val type = when (proxyType) {
            ProxyType.NO_PROXY -> Proxy.Type.DIRECT
            ProxyType.HTTP -> Proxy.Type.HTTP
            ProxyType.SOCKS -> Proxy.Type.SOCKS
        }

        return if (type != Proxy.Type.DIRECT && !proxyHostname.isNullOrBlank() && proxyPort != null) {
            Proxy(type, InetSocketAddress(proxyHostname, proxyPort))
        } else {
            Proxy.NO_PROXY
        }
    }
}