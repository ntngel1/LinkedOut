package com.github.ntngel1.linkedout.proxy_settings.di

import com.github.ntngel1.linkedout.proxy_settings.SettingsBasedProxySelector
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.net.ProxySelector
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class ProxySettingsModule {

    @Binds
    abstract fun bindProxySelector(settingsBasedProxySelector: SettingsBasedProxySelector): ProxySelector
}