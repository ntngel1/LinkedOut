package com.github.ntngel1.linkedout.proxy_settings.entity

sealed class ProxyEntity {

    abstract val id: Int
    abstract val hostname: String
    abstract val port: Int

    /**
     * if true then it wasn't created by user and corresponds to internal logic
     * we're using this field for pinging proxy because before we ping it, we need to save it
     */
    abstract val isInternal: Boolean

    data class Http(
        override val id: Int = -1,
        override val hostname: String = "",
        override val port: Int = 80,
        override val isInternal: Boolean = false,
        val username: String = "",
        val password: String = ""
    ) : ProxyEntity()

    data class Socks5(
        override val id: Int = -1,
        override val hostname: String = "",
        override val port: Int = 80,
        override val isInternal: Boolean = false,
        val username: String = "",
        val password: String = ""
    ) : ProxyEntity()

    data class MtProto(
        override val id: Int = -1,
        override val hostname: String = "",
        override val port: Int = 80,
        override val isInternal: Boolean = false,
        val secret: String = ""
    ) : ProxyEntity()

    fun copyIsInternal(isInternal: Boolean = this.isInternal): ProxyEntity =
        when (this) {
            is Http -> this.copy(isInternal = isInternal)
            is Socks5 -> this.copy(isInternal = isInternal)
            is MtProto -> this.copy(isInternal = isInternal)
        }
}