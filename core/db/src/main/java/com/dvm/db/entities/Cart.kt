package com.dvm.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite")
class Cart (
    @PrimaryKey(autoGenerate = true)
    val id: Int
)