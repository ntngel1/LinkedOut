/*
 * Copyright (c) 9.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.linkedout.lib_delegate_adapter.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

class ItemHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    var item: Item<*>? = null
}