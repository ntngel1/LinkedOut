/*
 * Copyright (c) 7.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.linkedout.lib_delegate_adapter.core

import android.content.Context
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import kotlinx.android.extensions.LayoutContainer

abstract class Item<T : Item<T>> {

    abstract val id: String

    @get:LayoutRes
    abstract val layoutId: Int

    val viewType: Int
        get() = layoutId

    /**
     * Make sure you call super.bind(view, viewStateStore) at the end of the method.
     * If you call this right before your own code, it can cause incorrect state restoring
     */
    @CallSuper
    open fun bind(layout: LayoutContainer, viewStateStore: ViewStateStore) {
        restoreState(layout, viewStateStore)
    }

    open fun areContentsTheSame(previousItem: T) = equals(previousItem)

    open fun bind(previousItem: T, layout: LayoutContainer, viewStateStore: ViewStateStore) {
        saveState(layout, viewStateStore)
        bind(layout, viewStateStore)
    }

    @CallSuper
    open fun recycle(layout: LayoutContainer, viewStateStore: ViewStateStore) {
        saveState(layout, viewStateStore)
    }

    open fun saveState(layout: LayoutContainer, viewStateStore: ViewStateStore) {}
    open fun restoreState(layout: LayoutContainer, viewStateStore: ViewStateStore) {}

    // Extension property for easier accessing Context
    protected val LayoutContainer.context: Context
        get() = containerView!!.context
}