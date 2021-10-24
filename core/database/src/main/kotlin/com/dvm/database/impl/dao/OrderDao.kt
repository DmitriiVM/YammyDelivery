package com.dvm.database.impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.dvm.database.api.models.Order
import com.dvm.database.api.models.OrderData
import com.dvm.database.api.models.OrderItem
import com.dvm.database.api.models.OrderStatus
import com.dvm.database.api.models.OrderWithItems
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

    @Query(
        """
            DELETE 
            FROM orders
            WHERE active = 0
        """
    )
    suspend fun deleteInactiveOrders()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrders(order: List<Order>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(orderItems: List<OrderItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderStatuses(orderStatuses: List<OrderStatus>)

    @Query(
        """
            DELETE FROM orders
        """
    )
    fun deleteOrders()
}