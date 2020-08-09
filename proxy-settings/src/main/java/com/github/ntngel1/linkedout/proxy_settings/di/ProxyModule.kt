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
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ProxyModule {

    @Binds
    abstract fun bindProxyGateway(
        proxyGatewayImp: ProxyGatewayImp
    ): ProxyGateway

    @Binds
    abstract fun bindGetProxyUseCase(
        getProxyUseCaseImp: GetProxyUseCaseImp
    ): GetProxyUseCase

    @Binds
    abstract fun bindProxyPingGateway(
        proxyPingGatewayImp: ProxyPingGatewayImp
    ): ProxyPingGateway

    @Binds
    abstract fun bindPingProxyUseCase(
        pingProxyUseCaseImp: PingProxyUseCaseImp
    ): PingProxyUseCase
}