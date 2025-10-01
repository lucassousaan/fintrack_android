package com.example.fintrack.domain.usecases

import com.example.fintrack.domain.model.CategorySpending
import com.example.fintrack.domain.repository.TransactionRepository
import javax.inject.Inject

class GetSpendingByCategoryUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(startDate: Long, endDate: Long): List<CategorySpending> {
        return repository.getSpendingByCategory(startDate = startDate, endDate = endDate)
    }
}