package com.github.ntngel1.linkedout.authorization.presentation.confirmation_code

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.github.ntngel1.linkedout.authorization.R
import com.github.ntngel1.linkedout.lib_utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationConfirmationCodeFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_authorization_confirmation_code

    private val viewModel: AuthorizationConfirmationCodeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        private const val PHONE_NUMBER_KEY = "phone_number"

        fun newInstance(phoneNumber: String) = AuthorizationConfirmationCodeFragment().apply {
            arguments = bundleOf(PHONE_NUMBER_KEY to phoneNumber)
        }
    }
}