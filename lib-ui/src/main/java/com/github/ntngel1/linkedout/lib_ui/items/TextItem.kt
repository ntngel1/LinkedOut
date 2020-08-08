package com.github.ntngel1.linkedout.lib_ui.items

import android.os.Build
import androidx.annotation.StyleRes
import com.github.ntngel1.linkedout.lib_delegate_adapter.core.Item
import com.github.ntngel1.linkedout.lib_ui.R
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.extensions.LayoutContainer

data class TextItem(
    override val id: String,
    val text: CharSequence,
    @StyleRes val textAppearanceResId: Int = R.style.TextAppearance_MaterialComponents_Body1
) : Item<TextItem>() {

    override val layoutId: Int
        get() = R.layout.item_text

    override fun bind(layout: LayoutContainer) {
        super.bind(layout)
        val textView = layout.containerView as MaterialTextView
        bindText(textView)
        bindTextAppearance(textView)
    }

    override fun bind(previousItem: TextItem, layout: LayoutContainer) {
        super.bind(previousItem, layout)
        val textView = layout.containerView as MaterialTextView

        if (text != previousItem.text) {
            bindText(textView)
        }

        if (textAppearanceResId != previousItem.textAppearanceResId) {
            bindTextAppearance(textView)
        }
    }

    private fun bindText(textView: MaterialTextView) {
        textView.text = text
    }

    private fun bindTextAppearance(textView: MaterialTextView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextAppearance(textAppearanceResId)
        } else {
            textView.setTextAppearance(textView.context, textAppearanceResId)
        }
    }
}