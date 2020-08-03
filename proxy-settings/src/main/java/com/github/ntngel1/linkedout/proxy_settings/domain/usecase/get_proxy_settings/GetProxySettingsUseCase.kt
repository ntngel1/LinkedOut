package com.github.ntngel1.linkedout.proxy_settings.domain.usecase.get_proxy_settings

import com.github.ntngel1.linkedout.proxy_settings.entity.ProxySettingsEntity

interface GetProxySettingsUseCase {
    suspend operator fun invoke(): ProxySettingsEntity
}