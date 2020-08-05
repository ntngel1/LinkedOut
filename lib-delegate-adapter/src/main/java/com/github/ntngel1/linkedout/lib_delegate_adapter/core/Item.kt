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

abstract class   Item<T : Item<T>> {

    abstract val id: String

    @get:LayoutRes
    abstract val layoutId: Int

    val viewType: Int
        get() = layoutId

    open fun bind(layout: LayoutContainer) {}
    open fun bind(previousItem: T, layout: LayoutContainer) {}
    open fun recycle(layout: LayoutContainer) {}

    open fun saveState(layout: LayoutContainer, viewStateStore: ViewStateStore) {}
    open fun restoreState(layout: LayoutContainer, viewStateStore: ViewStateStore) {}

    open fun areContentsTheSame(previousItem: T) = equals(previousItem)

    // Extensions

    /** For easier accessing Context through LayoutContainer */
    protected val LayoutContainer.context: Context
        get() = containerView!!.context

    fun <T> ViewStateStore.get(key: String): T? = get(id, key)

    fun <T> ViewStateStore.put(key: String, value: T?) = put(id, key, value)
}