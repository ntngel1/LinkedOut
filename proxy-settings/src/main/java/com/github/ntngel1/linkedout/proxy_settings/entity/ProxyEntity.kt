package com.github.ntngel1.linkedout.proxy_settings.entity

sealed class ProxyEntity {

    abstract val id: Int
    abstract val hostname: String
    abstract val port: Int
    abstract val isEnabled: Boolean

    /**
     * if true then it wasn't created by user and corresponds to internal logic
     * we're using this field for pinging proxy because before we ping it, we need to save it
     */
    abstract val isInternal: Boolean

    data class Http(
        override val id: Int = -1,
        override val hostname: String = "",
        override val port: Int = 80,
        override val isEnabled: Boolean = false,
        override val isInternal: Boolean = false,
        val username: String = "",
        val password: String = ""
    ) : ProxyEntity()

    data class Socks5(
        override val id: Int = -1,
        override val hostname: String = "",
        override val port: Int = 80,
        override val isEnabled: Boolean = false,
        override val isInternal: Boolean = false,
        val username: String = "",
        val password: String = ""
    ) : ProxyEntity()

    data class MtProto(
        override val id: Int = -1,
        override val hostname: String = "",
        override val port: Int = 80,
        override val isEnabled: Boolean = false,
        override val isInternal: Boolean = false,
        val secret: String = ""
    ) : ProxyEntity()
}

fun ProxyEntity.copyIsInternal(isInternal: Boolean = this.isInternal): ProxyEntity =
    when (this) {
        is ProxyEntity.Http -> this.copy(isInternal = isInternal)
        is ProxyEntity.Socks5 -> this.copy(isInternal = isInternal)
        is ProxyEntity.MtProto -> this.copy(isInternal = isInternal)
    }

fun ProxyEntity.usernameOrNull(): String? =
    when (this) {
        is ProxyEntity.Http -> this.username
        is ProxyEntity.Socks5 -> this.username
        is ProxyEntity.MtProto -> null
    }

fun ProxyEntity.passwordOrNull(): String? =
    when (this) {
        is ProxyEntity.Http -> this.password
        is ProxyEntity.Socks5 -> this.password
        is ProxyEntity.MtProto -> null
    }

fun ProxyEntity.secretOrNull(): String? =
    when (this) {
        is ProxyEntity.MtProto -> this.secret
        is ProxyEntity.Http,
        is ProxyEntity.Socks5 -> null
    }