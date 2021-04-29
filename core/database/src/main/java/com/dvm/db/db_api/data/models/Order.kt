package com.dvm.db.db_api.data.models

import androidx.room.*

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
    val status: String,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    val items: List<OrderItem>
)