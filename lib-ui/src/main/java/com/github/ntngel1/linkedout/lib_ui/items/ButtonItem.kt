package com.github.ntngel1.linkedout.lib_ui.items

import com.github.ntngel1.linkedout.lib_delegate_adapter.Callback
import com.github.ntngel1.linkedout.lib_delegate_adapter.core.Item
import com.github.ntngel1.linkedout.lib_delegate_adapter.core.ViewStateStore
import com.github.ntngel1.linkedout.lib_ui.R
import com.google.android.material.button.MaterialButton
import kotlinx.android.extensions.LayoutContainer

data class ButtonItem(
    override val id: String,
    val text: String,
    val onClicked: Callback
) : Item<ButtonItem>() {

    override val layoutId: Int
        get() = R.layout.item_button

    override fun bind(layout: LayoutContainer) {
        val button = layout.containerView as MaterialButton
        bindText(button)
        bindListeners(button)
    }

    override fun bind(previousItem: ButtonItem, layout: LayoutContainer) {
        val button = layout.containerView as MaterialButton
        if (text != previousItem.text) {
            bindText(button)
        }
    }

    override fun recycle(layout: LayoutContainer) {
        layout.containerView?.setOnClickListener(null)
    }

    private fun bindText(button: MaterialButton) {
        button.text = text
    }

    private fun bindListeners(button: MaterialButton) {
        button.setOnClickListener {
            onClicked.invoke()
        }
    }
}