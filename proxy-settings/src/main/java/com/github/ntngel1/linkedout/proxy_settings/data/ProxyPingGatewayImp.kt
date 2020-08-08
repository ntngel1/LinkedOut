package com.github.ntngel1.linkedout.proxy_settings.data

import com.github.ntngel1.linkedout.lib_telegram.TelegramClient
import com.github.ntngel1.linkedout.proxy_settings.domain.gateway.ProxyPingGateway
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.drinkless.td.libcore.telegram.TdApi
import javax.inject.Inject
import kotlin.math.roundToLong

class ProxyPingGatewayImp @Inject constructor(
    private val telegramClient: TelegramClient
) : ProxyPingGateway {

    override suspend fun ping(proxyId: Int): Long {
        val seconds = telegramClient.send<TdApi.Seconds>(TdApi.PingProxy(proxyId))
        return (seconds.seconds * 1000.0).roundToLong()
    }
}