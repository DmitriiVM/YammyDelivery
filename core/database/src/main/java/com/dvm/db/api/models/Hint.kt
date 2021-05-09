package com.dvm.db.api.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "hints")
data class Hint(
    @PrimaryKey
    val query: String,
    val date: Date
)