package com.dvm.db.impl.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.db.api.models.CartItem
import com.dvm.db.api.models.CartItemDetails
import kotlinx.coroutines.flow.Flow

@Dao
internal interface  CartDao {

    @Query(
        """
            SELECT 
                *,
                dish.name,
                dish.image,
                dish.price
            FROM cart
            JOIN dish 
            ON cart.dishId = dish.id
            ORDER BY dish.name
        """
    )
    fun cartItems(): Flow<List<CartItemDetails>>

    @Query(
        """
            SELECT COUNT()
            FROM cart
        """
    )
    suspend fun getCount(): Int

    @Query(
        """
            DELETE 
            FROM cart
            WHERE dishId = :dishId
        """
    )
    suspend fun removeItem(dishId: String)

    @Query(
        """
            DELETE 
            FROM cart
        """
    )
    suspend fun clearCart()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cart: CartItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cart: List<CartItem>)
}