package com.github.ntngel1.linkedout.proxy_settings.domain.gateway

interface ProxyPingGateway {

    /**
     * @return ping latency in milliseconds
     */
    suspend fun ping(proxyId: Int): Long
}