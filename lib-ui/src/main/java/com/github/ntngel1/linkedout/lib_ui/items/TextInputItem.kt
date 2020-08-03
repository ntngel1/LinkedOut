package com.github.ntngel1.linkedout.lib_ui.items

import android.text.Editable
import android.text.TextWatcher
import com.github.ntngel1.linkedout.lib_delegate_adapter.Callback1
import com.github.ntngel1.linkedout.lib_delegate_adapter.core.Item
import com.github.ntngel1.linkedout.lib_delegate_adapter.core.ViewStateStore
import com.github.ntngel1.linkedout.lib_extensions.hideKeyboard
import com.github.ntngel1.linkedout.lib_ui.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_text_input.*

data class TextInputItem(
    override val id: String,
    val text: String,
    val hint: String,
    val onTextChanged: Callback1<CharSequence>
) : Item<TextInputItem>() {

    override val layoutId: Int
        get() = R.layout.item_text_input

    private val textWatcher by lazy {
        makeTextWatcher()
    }

    override fun bind(layout: LayoutContainer, viewStateStore: ViewStateStore) {
        layout.text_input_layout_text_input.hint = hint
        with(layout.text_input_edit_text_text_input) {
            setText(this@TextInputItem.text)
            addTextChangedListener(textWatcher)
            setOnFocusChangeListener { view, hasFocus ->
                if (!hasFocus) {
                    context.hideKeyboard(view)
                }
            }
        }

        super.bind(layout, viewStateStore)
    }

    override fun recycle(layout: LayoutContainer, viewStateStore: ViewStateStore) {
        super.recycle(layout, viewStateStore)
        with(layout.text_input_edit_text_text_input) {
            removeTextChangedListener(textWatcher)
            onFocusChangeListener = null
        }
    }

    override fun saveState(layout: LayoutContainer, viewStateStore: ViewStateStore) {
        super.saveState(layout, viewStateStore)
        viewStateStore.put(
            id,
            "cursor_position",
            layout.text_input_edit_text_text_input.selectionStart
        )
    }

    override fun restoreState(layout: LayoutContainer, viewStateStore: ViewStateStore) {
        super.restoreState(layout, viewStateStore)
        viewStateStore.get<Int>(id, "cursor_position")?.let { cursorPosition ->
            layout.text_input_edit_text_text_input.setSelection(cursorPosition)
        }
    }

    private fun makeTextWatcher() = object : TextWatcher {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged.invoke(s ?: "")
        }

        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    }
}