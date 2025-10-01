package com.example.fintrack.data.repository

import com.example.fintrack.data.local.TransactionDao
import com.example.fintrack.data.mappers.toDomain
import com.example.fintrack.data.mappers.toModel
import com.example.fintrack.domain.model.CategorySpending
import com.example.fintrack.domain.model.Transaction
import com.example.fintrack.domain.repository.TransactionRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {
    override suspend fun getTransactions(): List<Transaction> {
        return dao.getAllTransactions().map {
            it.toDomain()
        }
    }

    override suspend fun addTransaction(transaction: Transaction) {
        return dao.insert(transaction.toModel())
    }

    override suspend fun deleteTransaction(transactionId: Int) {
        return dao.delete(transactionId)
    }

    override suspend fun getLastFiveTransactions(): List<Transaction> {
        return dao.getLastFiveTransactions().map {
            it.toDomain()
        }
    }

    override suspend fun getSpendingByCategory(
        startDate: Long,
        endDate: Long
    ): List<CategorySpending> {
        return dao.getSpendingByCategory(startDate = startDate, endDate = endDate).map {
            it.toDomain()
        }
    }
}
