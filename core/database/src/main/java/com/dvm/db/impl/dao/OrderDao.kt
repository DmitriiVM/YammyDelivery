package com.dvm.db.impl.dao

import androidx.room.*
import com.dvm.db.api.models.*
import kotlinx.coroutines.flow.Flow

@Dao
internal interface OrderDao {

    @Query(
        """
            SELECT 
                *,
                (
                    SELECT name
                    FROM order_status
                    WHERE order_status.id = statusId
                ) as status
            FROM orders
            WHERE completed = 0
        """
    )
    fun activeOrders(): Flow<List<OrderData>>

    @Query(
        """
            SELECT
                *,
                (
                    SELECT name
                    FROM order_status
                    WHERE order_status.id = statusId
                ) as status
            FROM orders
            WHERE completed = 1
        """
    )
    fun completedOrders(): Flow<List<OrderData>>

    @Transaction
    @Query(
        """
            SELECT *
            FROM orders
            WHERE id = :orderId
        """
    )
    fun order(orderId: String): Flow<OrderWithItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrders(order: List<Order>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(orderItems: List<OrderItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderStatuses(orderStatuses: List<OrderStatus>)
}