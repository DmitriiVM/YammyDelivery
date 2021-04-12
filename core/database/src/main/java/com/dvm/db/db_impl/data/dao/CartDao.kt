package com.dvm.db.db_impl.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvm.db.db_api.data.models.Cart
import com.dvm.db.db_api.data.models.CartItem
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
    fun cartItems(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cart: Cart)

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
}