package com.github.ntngel1.linkedout.authorization.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sent_authorization_confirmation_code_info_table")
data class SentAuthorizationConfirmationCodeInfoRoomEntity(
    @PrimaryKey
    val phoneNumber: String,
    val confirmationCodeLength: Int,
    val updatedAt: Long
)