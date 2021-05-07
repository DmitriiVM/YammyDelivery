package com.dvm.db.api.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification")
data class Notification(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val text: String,
    val seen: Boolean
)