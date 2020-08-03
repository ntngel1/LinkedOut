/*
 * Copyright (c) 7.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.linkedout.lib_delegate_adapter.core

import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import kotlinx.android.extensions.LayoutContainer

abstract class Item<T : Item<T>> : LayoutContainer {

    /** overring this property from [LayoutContainer] to enable view caching */
    override var containerView: View? = null

    abstract val id: String

    @get:LayoutRes
    abstract val layoutId: Int

    val viewType: Int
        get() = layoutId

    @CallSuper
    open fun bind(view: View) {
        containerView = view
    }

    open fun areContentsTheSame(previousItem: T) = equals(previousItem)

    open fun bind(previousItem: T, view: View) = bind(view)

    @CallSuper
    open fun recycle(view: View) {
        containerView = null
    }
}