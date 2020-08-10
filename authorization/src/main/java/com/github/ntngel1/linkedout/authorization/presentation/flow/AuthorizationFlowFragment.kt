package com.github.ntngel1.linkedout.authorization.presentation.flow

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.github.ntngel1.linkedout.authorization.R
import com.github.ntngel1.linkedout.lib_utils.BaseFragment
import kotlinx.android.synthetic.main.fragment_authorization.*

class AuthorizationFlowFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_authorization

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        test.text = requireArguments().getString(TITLE_KEY)
    }

    companion object {
        private const val TITLE_KEY = "title"
        fun newInstance(title: String) = AuthorizationFlowFragment()
            .apply {
            arguments = bundleOf(TITLE_KEY to title)
        }
    }
}
