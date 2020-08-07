package com.github.ntngel1.linkedout.proxy_settings.domain.usecase.check_connection_quality

import com.github.ntngel1.linkedout.proxy_settings.domain.gateway.PingProxyGateway
import com.github.ntngel1.linkedout.proxy_settings.entity.ConnectionQualityEntity
import javax.inject.Inject

class CheckConnectionQualityUseCaseImp @Inject constructor(
    private val pingProxyGateway: PingProxyGateway
) : CheckConnectionQualityUseCase {

    override suspend fun invoke(): ConnectionQualityEntity {
        var totalLatencyMs = 0L
        var failedRequestsCount = 0

        repeat(PING_COUNT) {
            try {
                totalLatencyMs += pingProxyGateway.ping()
            } catch (t: Throwable) {
                failedRequestsCount += 1
            }
        }

        return ConnectionQualityEntity(
            latencyMs = if (failedRequestsCount != PING_COUNT) {
                totalLatencyMs / (PING_COUNT - failedRequestsCount)
            } else {
                null
            },
            // TODO: Make stability evaluation more precise
            stability = when (failedRequestsCount) {
                0, 1 -> ConnectionQualityEntity.Stability.GOOD
                2 -> ConnectionQualityEntity.Stability.NORMAL
                else -> ConnectionQualityEntity.Stability.BAD
            }
        )
    }

    companion object {
        private const val PING_COUNT = 3
    }
}