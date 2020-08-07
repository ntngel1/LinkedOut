package com.github.ntngel1.linkedout.proxy_settings.di

import com.github.ntngel1.linkedout.proxy_settings.data.PingProxyGatewayImp
import com.github.ntngel1.linkedout.proxy_settings.data.ProxyGatewayImp
import com.github.ntngel1.linkedout.proxy_settings.domain.gateway.PingProxyGateway
import com.github.ntngel1.linkedout.proxy_settings.domain.gateway.ProxyGateway
import com.github.ntngel1.linkedout.proxy_settings.domain.usecase.check_connection_quality.CheckConnectionQualityUseCase
import com.github.ntngel1.linkedout.proxy_settings.domain.usecase.check_connection_quality.CheckConnectionQualityUseCaseImp
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
    abstract fun bindPingRepository(pingRepositoryImp: PingProxyGatewayImp): PingProxyGateway

    @Binds
    abstract fun bindCheckConnectionQualityUseCase(
        checkConnectionQualityUseCaseImp: CheckConnectionQualityUseCaseImp
    ): CheckConnectionQualityUseCase
}