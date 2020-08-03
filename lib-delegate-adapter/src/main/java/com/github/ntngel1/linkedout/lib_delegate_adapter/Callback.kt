/*
 * Copyright (c) 7.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.linkedout.lib_delegate_adapter

sealed class Callback {

    abstract val listener: () -> Unit

    operator fun invoke() {
        listener.invoke()
    }

    data class Hashable(
        override val listener: () -> Unit
    ) : Callback() {

        override fun equals(other: Any?): Boolean {
            return false
        }

        override fun hashCode(): Int {
            return listener.hashCode()
        }
    }

    data class NonHashable(
        override val listener: () -> Unit
    ) : Callback() {

        override fun equals(other: Any?): Boolean {
            return other !is Hashable
        }

        override fun hashCode(): Int {
            return listener.hashCode()
        }
    }
}

sealed class Callback1<T1> {

    abstract val listener: (T1) -> Unit

    operator fun invoke(t1: T1) {
        listener.invoke(t1)
    }

    data class Hashable<T1>(
        override val listener: (T1) -> Unit
    ) : Callback1<T1>() {

        override fun equals(other: Any?): Boolean {
            return false
        }

        override fun hashCode(): Int {
            return listener.hashCode()
        }
    }

    data class NonHashable<T1>(
        override val listener: (T1) -> Unit
    ) : Callback1<T1>() {

        override fun equals(other: Any?): Boolean {
            return other !is Hashable<*>
        }

        override fun hashCode(): Int {
            return listener.hashCode()
        }
    }
}

/**
 * @param static if false, item will be bound again even if no fields' values changed
 *               else if true, item will not be bound again if just callback changed
 */
fun callback(static: Boolean = false, listener: () -> Unit) =
    if (static) {
        Callback.NonHashable(
            listener
        )
    } else {
        Callback.Hashable(
            listener
        )
    }

/**
 * @param static if false, item will be bound again even if no fields' values changed
 *               else if true, item will not be bound again if just callback changed
 */
fun <T1> callback1(static: Boolean = false, listener: (T1) -> Unit) =
    if (static) {
        Callback1.NonHashable(
            listener
        )
    } else {
        Callback1.Hashable(
            listener
        )
    }