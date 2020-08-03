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

    override fun bind(layout: LayoutContainer, viewStateStore: ViewStateStore) {
        with(layout.containerView as MaterialButton) {
            text = this@ButtonItem.text
            setOnClickListener {
                onClicked.invoke()
            }
        }

        super.bind(layout, viewStateStore)
    }

    override fun recycle(layout: LayoutContainer, viewStateStore: ViewStateStore) {
        super.recycle(layout, viewStateStore)
        layout.containerView?.setOnClickListener(null)
    }
}