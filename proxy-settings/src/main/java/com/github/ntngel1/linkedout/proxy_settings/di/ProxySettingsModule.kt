package com.github.ntngel1.linkedout.proxy_settings.di

import com.github.ntngel1.linkedout.proxy_settings.data.ProxyPingGatewayImp
import com.github.ntngel1.linkedout.proxy_settings.data.ProxyGatewayImp
import com.github.ntngel1.linkedout.proxy_settings.domain.gateway.ProxyPingGateway
import com.github.ntngel1.linkedout.proxy_settings.domain.gateway.ProxyGateway
import com.github.ntngel1.linkedout.proxy_settings.domain.usecase.ping_proxy.PingProxyUseCase
import com.github.ntngel1.linkedout.proxy_settings.domain.usecase.ping_proxy.PingProxyUseCaseImp
import com.github.ntngel1.linkedout.proxy_settings.domain.usecase.get_proxy.GetProxyUseCase
import com.github.ntngel1.linkedout.proxy_settings.domain.usecase.get_proxy.GetProxyUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class ProxySettingsModule {

    @Binds
    abstract fun bindProxySettingsRepository(
        proxySettingsRepositoryImp: ProxyGatewayImp
    ): ProxyGateway

    @Binds
    abstract fun bindGetProxySettingsUseCase(
        getProxySettingsUseCaseImp: GetProxyUseCaseImp
    ): GetProxyUseCase

    @Binds
    abstract fun bindPingRepository(pingRepositoryImp: ProxyPingGatewayImp): ProxyPingGateway

    @Binds
    abstract fun bindCheckConnectionQualityUseCase(
        checkConnectionQualityUseCaseImp: PingProxyUseCaseImp
    ): PingProxyUseCase
}