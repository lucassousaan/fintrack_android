package com.example.fintrack.data.repository

import com.example.fintrack.domain.model.CategorySpending
import com.example.fintrack.domain.model.Transaction
import com.example.fintrack.domain.model.TransactionType
import com.example.fintrack.domain.repository.TransactionRepository

class FakeTransactionRepository: TransactionRepository {

    private val transactions = mutableListOf<Transaction>()

    fun clearTransactions() {
        transactions.clear()
    }

    fun addTransactionsForTest(transactionsToAdd: List<Transaction>) {
        transactions.addAll(transactionsToAdd)
    }

    override suspend fun getTransactions(): List<Transaction> {
        return transactions
    }

    override suspend fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }

    override suspend fun deleteTransaction(transactionId: Int) {
        transactions.removeIf { it.id == transactionId }
    }

    override suspend fun getLastFiveTransactions(): List<Transaction> {
        return transactions
            .sortedByDescending { it.date }
            .take(5)
    }

    override suspend fun getSpendingByCategory(startDate: Long, endDate: Long): List<CategorySpending> {
        return transactions
            .filter { it.type == TransactionType.EXPENSE && it.date in startDate..endDate }
            .groupBy { it.category }
            .map { (categoryName, transactionsInGroup) ->
                CategorySpending(
                    categoryName = categoryName,
                    totalAmount = transactionsInGroup.sumOf { it.amount }
                )
            }
    }

}