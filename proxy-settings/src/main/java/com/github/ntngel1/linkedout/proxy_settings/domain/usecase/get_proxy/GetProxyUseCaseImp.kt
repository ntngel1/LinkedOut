package com.github.ntngel1.linkedout.proxy_settings.domain.usecase.get_proxy

import com.github.ntngel1.linkedout.proxy_settings.domain.exception.NoSuchProxyException
import com.github.ntngel1.linkedout.proxy_settings.domain.gateway.ProxyGateway
import com.github.ntngel1.linkedout.proxy_settings.entity.ProxyEntity
import javax.inject.Inject

class GetProxyUseCaseImp @Inject constructor(
    private val proxyGateway: ProxyGateway
) : GetProxyUseCase {

    override suspend fun invoke(id: Int): ProxyEntity {
        return if (id == -1) {
            ProxyEntity.Http(hostname = "", port = 80, username = "", password = "")
        } else {
            proxyGateway.get(id)
                ?: throw NoSuchProxyException()
        }
    }
}