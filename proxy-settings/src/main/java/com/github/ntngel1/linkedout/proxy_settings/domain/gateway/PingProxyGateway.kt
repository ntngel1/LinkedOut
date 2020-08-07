package com.github.ntngel1.linkedout.proxy_settings.domain.gateway

interface PingProxyGateway {

    /**
     * @return ping latency in milliseconds
     */
    suspend fun ping(): Long
}