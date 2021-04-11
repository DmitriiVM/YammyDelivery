package com.dvm.db.db_impl.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.dvm.db.db_api.data.models.Cart

@Dao
internal interface  CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cart: Cart)



//    @Delete()
//    suspend fun clearCart(dishId: String)
}