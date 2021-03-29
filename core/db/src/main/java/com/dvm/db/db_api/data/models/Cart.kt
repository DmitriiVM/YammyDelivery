package com.dvm.db.db_api.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cart")
class Cart (
    @PrimaryKey(autoGenerate = true)
    val id: Int
)