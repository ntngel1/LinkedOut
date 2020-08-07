package com.github.ntngel1.linkedout.proxy_settings.data

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.github.ntngel1.linkedout.proxy_settings.domain.gateway.ProxyGateway
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyEntity
import com.google.gson.Gson
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi
import javax.inject.Inject

class ProxyGatewayImp @Inject constructor(
    private val telegramClient: Client
) : ProxyGateway {

    override suspend fun get(id: Int): ProxyEntity? {
        telegramClient.send(TdApi.GetProxies())
    }

    override suspend fun getAll(): List<ProxyEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun save(proxy: ProxyEntity?) {
        TODO("Not yet implemented")
    }
}