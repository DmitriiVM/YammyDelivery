package com.dvm.db.db_api.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey
    val id: String,
    val name: String,
    val order: Int,
    val parent: String? = null,
    val icon: String? = null,
    val active: Boolean,
    val createdAt: Long,   // TODO
    val updatedAt: Long   // TODO
)

data class ParentCategory(
    val id: String,
    val name: String,
    val icon: String
)

data class Subcategory(
    val id: String,
    val name: String,
    val parent: String
)