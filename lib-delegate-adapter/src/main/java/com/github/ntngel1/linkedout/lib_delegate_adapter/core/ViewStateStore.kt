package com.github.ntngel1.linkedout.lib_delegate_adapter.core

interface ViewStateStore {
    fun <T> get(itemId: String, key: String): T?
    fun put(itemId: String, key: String, value: Any?)
}