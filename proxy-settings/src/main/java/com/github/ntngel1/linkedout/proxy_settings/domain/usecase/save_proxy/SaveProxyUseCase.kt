package com.github.ntngel1.linkedout.proxy_settings.domain.usecase.save_proxy

import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyEntity

interface SaveProxyUseCase {
    suspend operator fun invoke(proxy: ProxyEntity)
}