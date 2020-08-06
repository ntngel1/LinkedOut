package com.github.ntngel1.linkedout.lib_ui.items.text_input

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import com.github.ntngel1.linkedout.lib_delegate_adapter.Callback2
import com.github.ntngel1.linkedout.lib_delegate_adapter.core.Item
import com.github.ntngel1.linkedout.lib_extensions.hideKeyboard
import com.github.ntngel1.linkedout.lib_extensions.text
import com.github.ntngel1.linkedout.lib_ui.ExtendedTextInputEditText
import com.github.ntngel1.linkedout.lib_ui.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_text_input.*

// TODO: So dirty item with some stupid crutches because of text watcher.
//       Need to make it more readable.
//       1. Simplify text watcher (remove unused method definitions like afterTextChanged/beforeTextChanged)
data class TextInputItem(
    override val id: String,
    val text: String,
    val hint: String,
    // first parameter - new text (CharSequence), second parameter - new cursor position (Int)
    val onTextChanged: Callback2<CharSequence, Int>,
    val inputType: Int = InputType.TYPE_CLASS_TEXT,
    val cursorPosition: Int = 0
) : Item<TextInputItem>() {

    override val layoutId: Int
        get() = R.layout.item_text_input

    private val textWatcher by lazy {
        makeTextWatcher()
    }

    private var editText: ExtendedTextInputEditText? = null

    override fun bind(layout: LayoutContainer) {
        editText = layout.text_input_edit_text_text_input

        bindText(layout)
        bindCursorPosition(layout)
        bindHint(layout)
        bindInputType(layout)
        bindOnTextChangedListener(layout)
        bindOnFocusChangedListener(layout)
    }

    override fun bind(previousItem: TextInputItem, layout: LayoutContainer) {
        editText = layout.text_input_edit_text_text_input
        bindOnTextChangedListener(layout)

        if (hint != previousItem.hint) {
            bindHint(layout)
        }

        if (text != previousItem.text || text != layout.text_input_edit_text_text_input.text()) {
            bindText(layout)
        }

        if (inputType != previousItem.inputType) {
            bindInputType(layout)
        }

        bindCursorPosition(layout)
    }

    override fun recycle(layout: LayoutContainer) {
        editText = null

        with(layout.text_input_edit_text_text_input) {
            removeAllTextChangedListeners()
            onFocusChangeListener = null
        }
    }

    private fun bindHint(layout: LayoutContainer) {
        layout.text_input_layout_text_input.hint = hint
    }

    private fun bindText(layout: LayoutContainer) {
        textWatcher.ignoreFirstTextChange = true
        layout.text_input_edit_text_text_input.setText(text)
    }

    private fun bindCursorPosition(layout: LayoutContainer) {
        // If index greater than text length, it can crash, so we're doing it just for safety
        val safeCursorPosition = if (cursorPosition <= text.length) {
            cursorPosition
        } else {
            text.length
        }

        layout.text_input_edit_text_text_input.setSelection(safeCursorPosition)
    }

    private fun bindInputType(layout: LayoutContainer) {
        layout.text_input_edit_text_text_input.inputType = inputType
    }

    private fun bindOnFocusChangedListener(layout: LayoutContainer) {
        layout.text_input_edit_text_text_input.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                layout.context.hideKeyboard(view)
            }
        }
    }

    private fun bindOnTextChangedListener(layout: LayoutContainer) {
        with(layout.text_input_edit_text_text_input) {
            removeAllTextChangedListeners()
            addTextChangedListener(textWatcher)
        }
    }

    private fun ExtendedTextInputEditText.updateText(newText: String) {
        removeTextChangedListener(textWatcher)
        setText(newText)
        addTextChangedListener(textWatcher)
    }

    private fun makeTextWatcher() = TextInputItemTextWatcher { text ->
        val cursorPosition = editText?.selectionStart
            ?: 0

        editText?.updateText(this.text)
        onTextChanged.invoke(
            text,
            cursorPosition
        )
    }
}