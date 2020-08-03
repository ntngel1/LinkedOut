package com.github.ntngel1.linkedout.proxy_settings.domain.usecase.check_connection_quality

import com.github.ntngel1.linkedout.proxy_settings.entity.ConnectionQualityEntity

interface CheckConnectionQualityUseCase {
    suspend operator fun invoke(): ConnectionQualityEntity
}