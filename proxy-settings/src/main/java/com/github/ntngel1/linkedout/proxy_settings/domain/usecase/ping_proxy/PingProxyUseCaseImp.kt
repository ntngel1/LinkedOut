package com.github.ntngel1.linkedout.proxy_settings.domain.usecase.ping_proxy

import com.github.ntngel1.linkedout.proxy_settings.domain.gateway.ProxyGateway
import com.github.ntngel1.linkedout.proxy_settings.domain.gateway.ProxyPingGateway
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyEntity
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyPingEntity
import com.github.ntngel1.linkedout.proxy_settings.entity.copyIsInternal
import javax.inject.Inject

// TODO: here we're saving proxy to gateway and then removing it, but what if we cancel this usecase
//       and this temporary proxy object will not be removed from gateway? Need to create some
//       background service (not android) that will clear all of temporary stuff that wasn't properly
//       removed
class PingProxyUseCaseImp @Inject constructor(
    private val proxyGateway: ProxyGateway,
    private val proxyPingGateway: ProxyPingGateway
) : PingProxyUseCase {

    override suspend fun invoke(proxy: ProxyEntity): ProxyPingEntity {
        val savedProxy = proxyGateway.add(
            proxy.copyIsInternal(isInternal = true)
        )

        var totalLatencyMs = 0L
        var failedPingsCount = 0
        repeat(PING_REQUEST_COUNT) {
            try {
                totalLatencyMs += proxyPingGateway.ping(savedProxy.id)
            } catch (t: Throwable) {
                failedPingsCount += 1
            }
        }

        proxyGateway.remove(savedProxy.id)

        val averageLatencyMs = totalLatencyMs / PING_REQUEST_COUNT
        return ProxyPingEntity(
            latencyMs = averageLatencyMs,
            stability = when (failedPingsCount) {
                0 -> ProxyPingEntity.Stability.GOOD
                in (1 until PING_REQUEST_COUNT / 2) -> ProxyPingEntity.Stability.NORMAL
                else -> ProxyPingEntity.Stability.BAD
            }
        )
    }

    companion object {
        private const val PING_REQUEST_COUNT = 5
    }
}