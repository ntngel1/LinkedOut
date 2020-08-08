package com.github.ntngel1.linkedout

import com.github.ntngel1.linkedout.authorization.AuthorizationFlowFragment
import com.github.ntngel1.linkedout.proxy_settings.presentation.proxy.ProxyFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object AuthorizationFlow : SupportAppScreen() {
        override fun getFragment() = AuthorizationFlowFragment()
    }

    object ProxySettings : SupportAppScreen() {
        override fun getFragment() = ProxyFragment.newInstance()
    }
}
