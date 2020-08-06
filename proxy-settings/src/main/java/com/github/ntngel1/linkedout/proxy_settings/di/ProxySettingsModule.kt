package com.github.ntngel1.linkedout.proxy_settings.di

import com.github.ntngel1.linkedout.proxy_settings.data.NetworkPingRepository
import com.github.ntngel1.linkedout.proxy_settings.data.ProxySettingsRepositoryImp
import com.github.ntngel1.linkedout.proxy_settings.domain.repository.PingRepository
import com.github.ntngel1.linkedout.proxy_settings.domain.repository.ProxySettingsRepository
import com.github.ntngel1.linkedout.proxy_settings.domain.usecase.check_connection_quality.CheckConnectionQualityUseCase
import com.github.ntngel1.linkedout.proxy_settings.domain.usecase.check_connection_quality.CheckConnectionQualityUseCaseImp
import com.github.ntngel1.linkedout.proxy_settings.domain.usecase.get_proxy_settings.GetProxySettingsUseCase
import com.github.ntngel1.linkedout.proxy_settings.domain.usecase.get_proxy_settings.GetProxySettingsUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.net.ProxySelector

@Module
@InstallIn(ApplicationComponent::class)
abstract class ProxySettingsModule {

    @Binds
    abstract fun bindProxySettingsRepository(
        proxySettingsRepositoryImp: ProxySettingsRepositoryImp
    ): ProxySettingsRepository

    @Binds
    abstract fun bindGetProxySettingsUseCase(
        getProxySettingsUseCaseImp: GetProxySettingsUseCaseImp
    ): GetProxySettingsUseCase

    @Binds
    abstract fun bindPingRepository(networkPingRepository: NetworkPingRepository): PingRepository

    @Binds
    abstract fun bindCheckConnectionQualityUseCase(
        checkConnectionQualityUseCaseImp: CheckConnectionQualityUseCaseImp
    ): CheckConnectionQualityUseCase
}