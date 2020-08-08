package com.github.ntngel1.linkedout.proxy_settings.domain.usecase.ping_proxy

import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyPingEntity
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyEntity

interface PingProxyUseCase {
    suspend operator fun invoke(proxy: ProxyEntity): ProxyPingEntity
}