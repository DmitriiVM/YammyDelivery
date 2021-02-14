package com.dvm.db.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface DishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDishes(dishes: List<Dish>)

}