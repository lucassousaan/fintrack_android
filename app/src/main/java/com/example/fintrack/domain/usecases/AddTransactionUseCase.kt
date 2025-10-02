package com.example.fintrack.domain.usecases

import com.example.fintrack.domain.exceptions.InvalidTransactionException
import com.example.fintrack.domain.model.Transaction
import com.example.fintrack.domain.repository.TransactionRepository
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        if (transaction.description.isBlank()) {
            throw InvalidTransactionException("A descrição não pode estar em branco.")
        }

        if (transaction.amount <= 0) {
            throw InvalidTransactionException("O valor da transação deve ser maior que zero.")
        }

        repository.addTransaction(transaction)
    }
}
