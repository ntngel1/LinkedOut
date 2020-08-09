package com.github.ntngel1.linkedout

import com.github.ntngel1.linkedout.authorization.AuthorizationFlowFragment
import com.github.ntngel1.linkedout.authorization.presentation.phone_number.AuthorizationPhoneNumberFragment
import com.github.ntngel1.linkedout.proxy_settings.presentation.proxy.ProxyFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object AuthorizationFlow : SupportAppScreen() {
        override fun getFragment() = AuthorizationFlowFragment()
    }

    object AuthorizationPhoneNumber : SupportAppScreen() {
        override fun getFragment() =
            AuthorizationPhoneNumberFragment()
    }

    data class AuthorizationTest(val title: String) : SupportAppScreen() {
        override fun getFragment() = AuthorizationFlowFragment.newInstance(title)
    }

    object CreateProxy : SupportAppScreen() {
        override fun getFragment() = ProxyFragment.newInstance()
    }

    data class EditProxy(val proxyId: Int) : SupportAppScreen() {
        override fun getFragment() = ProxyFragment.newInstance(proxyId)
    }
}
