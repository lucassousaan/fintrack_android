package com.example.fintrack.domain.usecases

import com.example.fintrack.domain.model.Transaction
import com.example.fintrack.domain.model.TransactionType
import com.example.fintrack.domain.repository.TransactionRepository
import javax.inject.Inject

class FilterTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(
        query: String? = null,
        type: TransactionType? = null,
        categoryName: String? = null,
        startDate: Long? = null,
        endDate: Long? = null
    ): List<Transaction> {
        return repository.filterTransactions(
            query = query,
            type = type,
            categoryName = categoryName,
            startDate = startDate,
            endDate = endDate
        )
    }
}