package com.github.ntngel1.linkedout.lib_ui

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.R
import com.google.android.material.textfield.TextInputEditText

class ExtendedTextInputEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle
) : TextInputEditText(context, attrs, defStyleAttr) {

    private val textChangedListeners by lazy {
        arrayListOf<TextWatcher>()
    }

    fun removeAllTextChangedListeners() {
        for (i in 0 until textChangedListeners.count()) {
            removeTextChangedListener(textChangedListeners[i])
        }

        textChangedListeners.clear()
    }

    override fun addTextChangedListener(watcher: TextWatcher) {
        super.addTextChangedListener(watcher)
        textChangedListeners.add(watcher)
    }
}