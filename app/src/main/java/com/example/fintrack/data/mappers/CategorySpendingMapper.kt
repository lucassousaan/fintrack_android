package com.example.fintrack.data.mappers

import com.example.fintrack.data.models.CategorySpendingDto
import com.example.fintrack.domain.model.CategorySpending


fun CategorySpendingDto.toDomain(): CategorySpending {
    return CategorySpending(
        categoryName = category,
        totalAmount = totalAmount
    )
}

fun CategorySpending.toModel(): CategorySpendingDto {
    return CategorySpendingDto(
        category = categoryName,
        totalAmount = totalAmount
    )
}
