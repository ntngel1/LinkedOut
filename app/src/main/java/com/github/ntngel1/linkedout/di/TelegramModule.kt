package com.github.ntngel1.linkedout.di

import com.github.ntngel1.linkedout.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.drinkless.td.libcore.telegram.Client
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object TelegramModule {

    @Provides
    @Singleton
    fun provideResultHandler(logger: Logger): Client.ResultHandler =
        Client.ResultHandler { result ->
            logger.d(result)
        }

    @Provides
    @Singleton
    fun provideExceptionHandler(logger: Logger): Client.ExceptionHandler =
        Client.ExceptionHandler { throwable ->
            logger.e(throwable)
        }

    @Provides
    @Singleton
    fun provideTelegramClient(
        resultHandler: Client.ResultHandler,
        exceptionHandler: Client.ExceptionHandler
    ): Client = Client.create(resultHandler, exceptionHandler, exceptionHandler)
}