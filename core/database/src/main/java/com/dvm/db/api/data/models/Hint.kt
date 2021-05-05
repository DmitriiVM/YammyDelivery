package com.dvm.db.api.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hints")
data class Hint(
    @PrimaryKey
    val query: String,
    val date: Long
)