package com.example.fintrack.domain.repository

import com.example.fintrack.domain.model.CategorySpending
import com.example.fintrack.domain.model.ReportSummary
import com.example.fintrack.domain.model.Transaction
import com.example.fintrack.domain.model.TransactionType

interface TransactionRepository {
    suspend fun getTransactions(): List<Transaction>
    suspend fun addTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transactionId: Int)
    suspend fun getLastFiveTransactions(): List<Transaction>
    suspend fun getSpendingByCategory(startDate: Long, endDate: Long): List<CategorySpending>
    suspend fun filterTransactions(
        query: String?,
        type: TransactionType?,
        categoryName: String?,
        startDate: Long?,
        endDate: Long?
    ): List<Transaction>
    suspend fun getReportSummary(startDate: Long, endDate: Long): ReportSummary
}