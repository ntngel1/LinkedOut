package com.github.ntngel1.linkedout

import com.github.ntngel1.linkedout.authorization.AuthorizationFlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object AuthorizationFlow : SupportAppScreen() {
        override fun getFragment() = AuthorizationFlowFragment()
    }
}
