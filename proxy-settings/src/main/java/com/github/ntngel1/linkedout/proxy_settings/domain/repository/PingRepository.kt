package com.github.ntngel1.linkedout.proxy_settings.domain.repository

interface PingRepository {

    /**
     * @return ping latency in milliseconds
     */
    suspend fun ping(): Long
}