package com.example.fintrack.domain.model

data class Transaction(
    val id: Int,
    val description: String,
    val amount: Double,
    val type: TransactionType,
    val category: String,
    val date: Long
)

enum class TransactionType {
    INCOME, EXPENSE
}
