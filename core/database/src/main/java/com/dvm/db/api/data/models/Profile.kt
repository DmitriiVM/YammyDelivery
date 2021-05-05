package com.dvm.db.api.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class Profile(
    val firstName: String,
    val lastName: String,
    @PrimaryKey
    val email: String
)