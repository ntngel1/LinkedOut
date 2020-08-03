package com.github.ntngel1.linkedout.proxy_settings.domain.usecase.get_proxy_settings

import com.github.ntngel1.linkedout.proxy_settings.domain.repository.ProxySettingsRepository
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxySettingsEntity
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyType
import javax.inject.Inject

class GetProxySettingsUseCaseImp @Inject constructor(
    private val proxySettingsRepository: ProxySettingsRepository
) : GetProxySettingsUseCase {

    override suspend fun invoke(): ProxySettingsEntity {
        return proxySettingsRepository.obtain()
            ?: DEFAULT_PROXY_SETTINGS
    }

    companion object {
        private val DEFAULT_PROXY_SETTINGS = ProxySettingsEntity(
            proxyType = ProxyType.DIRECT,
            proxyHostname = null
        )
    }
}