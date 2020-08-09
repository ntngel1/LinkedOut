package com.github.ntngel1.linkedout

import com.github.ntngel1.linkedout.lib_telegram.TelegramClient
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import org.drinkless.td.libcore.telegram.TdApi
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class AppLauncher @Inject constructor(
    private val router: Router,
    private val telegramClient: TelegramClient
) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    fun coldStart() = launch {
        router.newRootScreen(Screens.AuthorizationPhoneNumber)
        /*coroutineScope {
            launch {
                telegramClient.authorizationStatesChannel.asFlow()
                    .collect { state ->
                        Timber.d("APPLAUNCHER Received state $state")
                        when (state) {
                            is TdApi.AuthorizationStateWaitPhoneNumber -> {
                                telegramClient.execute(
                                    TdApi.SetAuthenticationPhoneNumber(
                                        "+79964051206",
                                        TdApi.PhoneNumberAuthenticationSettings(
                                            true,
                                            false,
                                            false
                                        )
                                    )
                                )
                                router.newRootChain(
                                    Screens.AuthorizationTest("PhoneNumber")
                                )

                                cancel()
                            }
                            is TdApi.AuthorizationStateWaitCode -> {
                                router.newRootChain(
                                    Screens.AuthorizationTest("PhoneNumber"),
                                    Screens.AuthorizationTest("WaitCode")
                                )
                                cancel()
                            }
                        }
                    }
            }
        }*/
    }
}
