package com.example.fintrack.domain.repository

import com.example.fintrack.domain.model.CategorySpending
import com.example.fintrack.domain.model.Transaction

interface TransactionRepository {
    suspend fun getTransactions(): List<Transaction>
    suspend fun addTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transactionId: Int)
    suspend fun getLastFiveTransactions(): List<Transaction>
    suspend fun getSpendingByCategory(startDate: Long, endDate: Long): List<CategorySpending>
}