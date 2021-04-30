package com.dvm.db.db_impl.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.db.db_api.data.models.*
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