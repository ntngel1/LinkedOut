package com.github.ntngel1.linkedout.proxy_settings.data

import com.github.ntngel1.linkedout.proxy_settings.domain.gateway.PingProxyGateway
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class PingProxyGatewayImp @Inject constructor(
    private val okHttpClient: OkHttpClient
) : PingProxyGateway {

    override suspend fun ping(): Long = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://linkedin.com")
            .get()
            .build()

        val response = okHttpClient.newCall(request).execute()

        response.receivedResponseAtMillis - response.sentRequestAtMillis
    }
}