package com.example.fintrack.domain.model

data class ReportSummary(
    val totalExpenses: Double,
    val largestExpense: Double,
    val distinctCategoriesCount: Int
)
