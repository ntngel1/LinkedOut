package com.github.ntngel1.linkedout.proxy_settings.domain.usecase.get_proxy

import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyEntity

interface GetProxyUseCase {
    suspend operator fun invoke(id: Int): ProxyEntity
}