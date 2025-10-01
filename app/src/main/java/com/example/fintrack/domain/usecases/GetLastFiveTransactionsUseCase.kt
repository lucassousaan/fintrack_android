package com.example.fintrack.domain.usecases

import com.example.fintrack.domain.model.Transaction
import com.example.fintrack.domain.repository.TransactionRepository
import javax.inject.Inject

class GetLastFiveTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(): List<Transaction> {
        return repository.getLastFiveTransactions()
    }
}
