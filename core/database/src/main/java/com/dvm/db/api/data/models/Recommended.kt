package com.dvm.db.api.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recommended")
class Recommended(
    @PrimaryKey
    val dishId: String
)