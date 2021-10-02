package com.dvm.database.api.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey
    val dishId: String
)