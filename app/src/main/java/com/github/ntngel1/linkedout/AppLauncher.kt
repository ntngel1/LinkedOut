package com.github.ntngel1.linkedout

import com.github.ntngel1.linkedout.lib_telegram.TelegramClient
import kotlinx.coroutines.runBlocking
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AppLauncher @Inject constructor(
    private val router: Router,
    private val telegramClient: TelegramClient
) {

    fun coldStart() = runBlocking {
        telegramClient.waitForInitialization()
        router.newRootScreen(Screens.CreateProxy)
    }
}
