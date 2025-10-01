package com.example.fintrack.data.mappers

import com.example.fintrack.data.models.TransactionModel
import com.example.fintrack.domain.model.Transaction
import com.example.fintrack.domain.model.TransactionType

fun TransactionModel.toDomain(): Transaction {
    val typeEnum = try {
        TransactionType.valueOf(type)
    } catch (e: IllegalArgumentException) {
        TransactionType.INCOME
    }

    return Transaction(
        id = id,
        amount = amount,
        description = description,
        date = date,
        type = typeEnum,
        category = category
    )
}

fun Transaction.toModel(): TransactionModel {
    return TransactionModel(
        id = id,
        amount = amount,
        type = type.name,
        category = category,
        date = date,
        description = description
    )
}
