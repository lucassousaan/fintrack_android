package com.example.fintrack.data.mappers

import com.example.fintrack.data.models.CategoryModel
import com.example.fintrack.domain.model.Category

fun CategoryModel.toDomain(): Category {
    return Category(
        name = name,
        colorHex = colorHex
    )
}

fun Category.toModel(): CategoryModel {
    return CategoryModel(
        name = name,
        colorHex = colorHex
    )
}
