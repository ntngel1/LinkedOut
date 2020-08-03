package com.github.ntngel1.linkedout.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import java.net.ProxySelector
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(proxySelector: ProxySelector): OkHttpClient =
        OkHttpClient.Builder()
            .proxySelector(proxySelector)
            .build()
}