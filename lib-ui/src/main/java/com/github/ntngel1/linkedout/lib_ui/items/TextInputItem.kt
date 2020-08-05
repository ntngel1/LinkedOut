package com.github.ntngel1.linkedout.lib_ui.items

import android.text.Editable
import android.text.InputType
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
    val onTextChanged: Callback1<CharSequence>,
    val inputType: Int = InputType.TYPE_CLASS_TEXT
) : Item<TextInputItem>() {

    override val layoutId: Int
        get() = R.layout.item_text_input

    private val textWatcher by lazy {
        makeTextWatcher()
    }

    override fun bind(layout: LayoutContainer) {
        bindHint(layout)
        bindText(layout)
        bindInputType(layout)
        bindListeners(layout)
    }

    override fun bind(previousItem: TextInputItem, layout: LayoutContainer) {
        if (hint != previousItem.hint) {
            bindHint(layout)
        }

        if (text != previousItem.text) {
            bindText(layout)
        }

        if (inputType != previousItem.inputType) {
            bindInputType(layout)
        }
    }

    override fun recycle(layout: LayoutContainer) {
        with(layout.text_input_edit_text_text_input) {
            removeTextChangedListener(textWatcher)
            onFocusChangeListener = null
        }
    }

    override fun saveState(layout: LayoutContainer, viewStateStore: ViewStateStore) {
        viewStateStore.put(
            CURSOR_POSITION_KEY,
            layout.text_input_edit_text_text_input.selectionStart
        )
    }

    override fun restoreState(layout: LayoutContainer, viewStateStore: ViewStateStore) {
        viewStateStore.get<Int>(CURSOR_POSITION_KEY)?.let { cursorPosition ->
            val text = layout.text_input_edit_text_text_input.text?.toString().orEmpty()
            val index = if (cursorPosition <= text.length) {
                cursorPosition
            } else {
                0
            }

            layout.text_input_edit_text_text_input.setSelection(index)
        }
    }

    private fun bindHint(layout: LayoutContainer) {
        layout.text_input_layout_text_input.hint = hint
    }

    private fun bindText(layout: LayoutContainer) {
        layout.text_input_edit_text_text_input.setText(text)
    }

    private fun bindInputType(layout: LayoutContainer) {
        layout.text_input_edit_text_text_input.inputType = inputType
    }

    private fun bindListeners(layout: LayoutContainer) =
        with(layout.text_input_edit_text_text_input) {
            addTextChangedListener(textWatcher)
            setOnFocusChangeListener { view, hasFocus ->
                if (!hasFocus) {
                    context.hideKeyboard(view)
                }
            }
        }

    private fun makeTextWatcher() = object : TextWatcher {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged.invoke(s?.toString() ?: "")
        }

        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    }

    companion object {
        private const val CURSOR_POSITION_KEY = "cursor_position"
    }
}