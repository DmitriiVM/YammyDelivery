package com.dvm.database.api.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val dishId: String,
    val author: String,
    val date: String,
    val rating: Int,
    val text: String?,
    val active: Boolean
)