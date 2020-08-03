package com.github.ntngel1.linkedout.lib_delegate_adapter.core

class HashMapViewStateStore : ViewStateStore {

    private val hashMap by lazy {
        HashMap<String, Any?>()
    }

    override fun put(itemId: String, key: String, value: Any?) {
        hashMap[internalKeyOf(itemId, key)] = value
    }

    override fun <T> get(itemId: String, key: String): T? {
        return hashMap[internalKeyOf(itemId, key)]?.let { value ->
            value as T
        }
    }

    private fun internalKeyOf(itemId: String, key: String) = "${itemId}_$key"
}