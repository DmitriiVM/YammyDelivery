package com.dvm.db.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DishDao {


//    @Query(
//        """
//            SELECT COUNT(oldPrice)
//            FROM dish
//            WHERE oldPrice > 0
//        """
//    )
//    suspend fun hasSpecialOffers() : Int

    @Query(
        """
            SELECT EXISTS(
                SELECT COUNT(oldPrice)
                FROM dish
                WHERE oldPrice > 0
            )

        """
    )
    suspend fun hasSpecialOffers(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDishes(dishes: List<Dish>)

}