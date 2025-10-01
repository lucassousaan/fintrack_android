package com.example.fintrack.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryModel(
    @PrimaryKey val name: String,
    val colorHex: String
)
