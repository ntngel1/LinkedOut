package com.github.ntngel1.linkedout

import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AppLauncher @Inject constructor(
    private val router: Router
) {

    fun coldStart() {
        router.newRootScreen(Screens.AuthorizationFlow)
    }
}
