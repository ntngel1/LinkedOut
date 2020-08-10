package com.github.ntngel1.linkedout.authorization.presentation.phone_number

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.ntngel1.linkedout.authorization.presentation.flow.AuthorizationFlowFragment
import com.github.ntngel1.linkedout.authorization.R
import com.github.ntngel1.linkedout.lib_utils.BaseFragment
import com.github.ntngel1.linkedout.lib_utils.text
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_authorization_phone_number.*
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen
import javax.inject.Inject

@AndroidEntryPoint
class AuthorizationPhoneNumberFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_authorization_phone_number

    @Inject
    lateinit var router: Router

    private val viewModel: AuthorizationPhoneNumberViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContinueButton()
        viewModel.events.observe(::handleEvent)
    }

    private fun setupContinueButton() {
        material_button_authorization_phone_number_continue.setOnClickListener {
            val phoneNumber = text_input_edit_text_authorization_phone_number.text()
            viewModel.onContinueClicked(phoneNumber)
        }
    }

    private fun handleEvent(event: AuthorizationPhoneNumberEvent) = when (event) {
        is AuthorizationPhoneNumberEvent.ShowAuthorizationConfirmationCodeScreen -> {
            router.navigateTo(object : SupportAppScreen() {
                override fun getFragment(): Fragment? {
                    return AuthorizationFlowFragment.newInstance("SENT CODE")
                }
            })
        }
    }
}