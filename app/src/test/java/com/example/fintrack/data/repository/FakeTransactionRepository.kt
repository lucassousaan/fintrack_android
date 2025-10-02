package com.example.fintrack.data.repository

import com.example.fintrack.domain.model.CategorySpending
import com.example.fintrack.domain.model.ReportSummary
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

    override suspend fun filterTransactions(
        query: String?,
        type: TransactionType?,
        categoryName: String?,
        startDate: Long?,
        endDate: Long?
    ): List<Transaction> {
        var result = transactions.toList()

        query?.let {
            if (it.isNotBlank()) {
                result = result.filter { t -> t.description.contains(it, ignoreCase = true) }
            }
        }
        type?.let {
            result = result.filter { t -> t.type == it }
        }
        categoryName?.let {
            result = result.filter { t -> t.category == it }
        }
        if (startDate != null && endDate != null) {
            result = result.filter { t -> t.date in startDate..endDate }
        }

        return result.sortedByDescending { it.date }
    }

    override suspend fun getReportSummary(startDate: Long, endDate: Long): ReportSummary {
        val expensesInPeriod = transactions.filter {
            it.type == TransactionType.EXPENSE && it.date in startDate..endDate
        }

        val totalExpenses = expensesInPeriod.sumOf { it.amount }
        val largestExpense = expensesInPeriod.maxOfOrNull { it.amount } ?: 0.0
        val distinctCategoriesCount = expensesInPeriod.map { it.category }.distinct().count()

        return ReportSummary(
            totalExpenses = totalExpenses,
            largestExpense = largestExpense,
            distinctCategoriesCount = distinctCategoriesCount
        )
    }

}