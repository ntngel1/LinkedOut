package com.github.ntngel1.linkedout.proxy_settings.data

import com.github.ntngel1.linkedout.lib_telegram.TelegramClient
import com.github.ntngel1.linkedout.proxy_settings.domain.gateway.ProxyGateway
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyEntity
import kotlinx.coroutines.delay
import org.drinkless.td.libcore.telegram.TdApi
import javax.inject.Inject

class ProxyGatewayImp @Inject constructor(
    private val telegramClient: TelegramClient
) : ProxyGateway {

    override suspend fun add(proxy: ProxyEntity): ProxyEntity {
        val proxyType = when (proxy) {
            is ProxyEntity.Http -> TdApi.ProxyTypeHttp(
                proxy.username,
                proxy.password,
                true
            )
            is ProxyEntity.Socks5 -> TdApi.ProxyTypeSocks5(
                proxy.username,
                proxy.password
            )
            is ProxyEntity.MtProto -> TdApi.ProxyTypeMtproto(
                proxy.secret
            )
        }

        val query = TdApi.AddProxy(
            proxy.hostname,
            proxy.port,
            true,
            proxyType
        )

        val result = telegramClient.send(query)
        if (result is TdApi.Error) {
            throw Exception("TDLib error: code = ${result.code}, message = ${result.message}")
        }

        return (result as TdApi.Proxy).toProxyEntity()
    }

    override suspend fun get(id: Int): ProxyEntity? {
        val result = telegramClient.send(TdApi.GetProxies())
        if (result is TdApi.Error) {
            throw Exception("TDLib error: code = ${result.code}, message = ${result.message}")
        }

        val proxies = result as TdApi.Proxies
        return proxies.proxies
            .find { it.id == id }
            ?.toProxyEntity()
    }

    override suspend fun getAll(): List<ProxyEntity> {
        val result = telegramClient.send(TdApi.GetProxies())
        if (result is TdApi.Error) {
            throw Exception("TDLib error: code = ${result.code}, message = ${result.message}")
        }

        val proxies = result as TdApi.Proxies
        return proxies.proxies
            .map { it.toProxyEntity() }
    }

    override suspend fun update(proxy: ProxyEntity?) {
        TODO("Not yet implemented")
    }

    override suspend fun remove(id: Int) {
        val result = telegramClient.send(TdApi.RemoveProxy(id))
        if (result is TdApi.Error) {
            throw Exception("TDLib error: code = ${result.code}, message = ${result.message}")
        }
    }

    private fun TdApi.Proxy.toProxyEntity(): ProxyEntity =
        when (val type = type) {
            is TdApi.ProxyTypeHttp -> ProxyEntity.Http(
                id = id,
                hostname = server,
                port = port,
                username = type.username,
                password = type.password
            )
            is TdApi.ProxyTypeSocks5 -> ProxyEntity.Socks5(
                id = id,
                hostname = server,
                port = port,
                username = type.username,
                password = type.password
            )
            is TdApi.ProxyTypeMtproto -> ProxyEntity.MtProto(
                id = id,
                hostname = server,
                port = port,
                secret = type.secret
            )
            else -> throw IllegalStateException("Cannot handle ProxyType = $type")
        }
}