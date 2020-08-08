package com.github.ntngel1.linkedout.proxy_settings.domain.gateway

import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyEntity

interface ProxyGateway {
    suspend fun add(proxy: ProxyEntity): ProxyEntity
    suspend fun getAll(): List<ProxyEntity>
    suspend fun get(id: Int): ProxyEntity?
    suspend fun update(proxy: ProxyEntity?)
    suspend fun remove(id: Int)
}