package com.dvm.db.api.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_status")
data class OrderStatus(
    @PrimaryKey
    val id: String,
    val name: String,
    val cancelable: Boolean,
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)