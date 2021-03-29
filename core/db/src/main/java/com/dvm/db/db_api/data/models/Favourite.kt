package com.dvm.db.db_api.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val dishId: String
)