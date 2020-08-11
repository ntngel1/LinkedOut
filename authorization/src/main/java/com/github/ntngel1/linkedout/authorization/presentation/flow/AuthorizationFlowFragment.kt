package com.github.ntngel1.linkedout.authorization.presentation.flow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.github.ntngel1.linkedout.authorization.R
import com.github.ntngel1.linkedout.lib_utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationFlowFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_authorization

    private val viewModel: AuthorizationFlowViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.events.observe(::handleEvent)
    }

    private fun handleEvent(event: AuthorizationFlowEvent) {
        when (event) {
            is AuthorizationFlowEvent.ShowPhoneNumberScreen -> {

            }
        }
    }
}
