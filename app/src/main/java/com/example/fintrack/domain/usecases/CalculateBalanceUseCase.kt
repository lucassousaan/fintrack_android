package com.example.fintrack.domain.usecases

import com.example.fintrack.domain.model.TransactionType
import com.example.fintrack.domain.repository.TransactionRepository
import javax.inject.Inject

class CalculateBalanceUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(): Double {
        val transactions = repository.getTransactions()
        val totalIncome = transactions
            .filter { it.type == TransactionType.INCOME }
            .sumOf { it.amount }
        val totalExpense = transactions
            .filter { it.type == TransactionType.EXPENSE }
            .sumOf { it.amount }
        return totalIncome - totalExpense
    }
}
