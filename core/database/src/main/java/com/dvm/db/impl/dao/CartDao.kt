package com.dvm.db.impl.dao

import androidx.room.*
import com.dvm.db.api.models.CartItem
import com.dvm.db.api.models.CartItemDetails
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class CartDao {

    @Query(
        """
            SELECT 
                *,
                dish.name,
                dish.image,
                dish.price
            FROM cart
            JOIN dish ON cart.dishId = dish.id
            ORDER BY dish.name
        """
    )
    abstract fun cartItems(): Flow<List<CartItemDetails>>

    @Query(
        """
            SELECT COUNT()
            FROM cart
        """
    )
    abstract suspend fun getCartCount(): Int

    @Query(
        """
            SELECT quantity
            FROM cart
            WHERE dishId = :dishId
        """
    )
    abstract suspend fun getDishQuantity(dishId: String): Int

    @Query(
        """
            DELETE 
            FROM cart
        """
    )
    abstract suspend fun clearCart()

    @Query(
        """
            UPDATE cart 
            SET quantity = quantity + 1
            WHERE dishId = :dishId
        """
    )
    abstract suspend fun addPiece(dishId: String)

    @Transaction
    open suspend fun removePiece(dishId: String) {
        if (getDishQuantity(dishId) <= 1) {
            removeDishFromCart(dishId)
        } else {
            removeDishPiece(dishId)
        }
    }

    @Query(
        """
            UPDATE cart 
            SET quantity = quantity - 1
            WHERE dishId = :dishId
        """
    )
    abstract suspend fun removeDishPiece(dishId: String)

    @Query(
        """
            DELETE 
            FROM cart
            WHERE dishId = :dishId
        """
    )
    abstract suspend fun removeDishFromCart(dishId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCartItem(cart: CartItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCartItems(cart: List<CartItem>)
}