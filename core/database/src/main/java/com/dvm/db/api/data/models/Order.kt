package com.dvm.db.api.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey
    val id: String,
    val total: Int,
    val address: String,
    val statusId: String,
    val active: Boolean,
    val completed: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
)

data class OrderData(
    val id: String,
    val createdAt: Long,
    val total: Int,
    val address: String,
    val status: String,
)

data class OrderWithItems(
    val id: String,
    val createdAt: Long,
    val total: Int,
    val address: String,
    val statusId: String,
    val completed: Boolean,
    @Relation(
        parentColumn = "statusId",
        entityColumn = "id"
    )
    val status: OrderStatus,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    val items: List<OrderItem>
)