package com.example.fintrack.domain.usecases

import com.example.fintrack.domain.repository.TransactionRepository
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transactionId: Int) {
        repository.deleteTransaction(transactionId)
    }
}
