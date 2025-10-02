package com.example.fintrack.domain.usecases

import com.example.fintrack.domain.model.ReportSummary
import com.example.fintrack.domain.repository.TransactionRepository
import javax.inject.Inject

class GetReportSummaryUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(startDate: Long, endDate: Long): ReportSummary {
        return repository.getReportSummary(startDate, endDate)
    }
}
