package com.github.ntngel1.linkedout.authorization.presentation.confirmation_code

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.github.ntngel1.linkedout.authorization.R
import com.github.ntngel1.linkedout.lib_utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationConfirmationCodeFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_authorization_confirmation_code

    private val viewModel: AuthorizationConfirmationCodeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.setup(
                requireArguments().getString(PHONE_NUMBER_KEY).orEmpty(),
                requireArguments().getInt(CONFIRMATION_CODE_LENGTH_KEY)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(::renderState)
    }

    private fun renderState(state: AuthorizationConfirmationCodeState) {

    }

    companion object {

        private const val PHONE_NUMBER_KEY = "phone_number"
        private const val CONFIRMATION_CODE_LENGTH_KEY = "confirmation_code_length"

        fun newInstance(
            phoneNumber: String,
            confirmationCodeLength: Int
        ) = AuthorizationConfirmationCodeFragment().apply {
            arguments = bundleOf(
                PHONE_NUMBER_KEY to phoneNumber,
                CONFIRMATION_CODE_LENGTH_KEY to confirmationCodeLength
            )
        }
    }
}