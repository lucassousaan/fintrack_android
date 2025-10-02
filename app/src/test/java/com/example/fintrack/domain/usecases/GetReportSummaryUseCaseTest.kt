package com.example.fintrack.domain.usecases

import com.example.fintrack.data.repository.FakeTransactionRepository
import com.example.fintrack.domain.model.Transaction
import com.example.fintrack.domain.model.TransactionType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetReportSummaryUseCaseTest {

    private lateinit var fakeRepository: FakeTransactionRepository
    private lateinit var getReportSummaryUseCase: GetReportSummaryUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeTransactionRepository()
        getReportSummaryUseCase = GetReportSummaryUseCase(fakeRepository)
    }

    @Test
    fun `retorna transacoes corretamente`() = runTest {
        val transactions = listOf(
            Transaction(1, "Supermercado", 200.0, TransactionType.EXPENSE, "Alimentação", 150L),
            Transaction(2, "Cinema", 50.0, TransactionType.EXPENSE, "Lazer", 160L),
            Transaction(3, "Padaria", 25.5, TransactionType.EXPENSE, "Alimentação", 170L),
            Transaction(4, "Presente", 80.0, TransactionType.EXPENSE, "Outros", 90L),
            Transaction(5, "Salário", 2000.0, TransactionType.INCOME, "Salário", 180L)
        )
        fakeRepository.addTransactionsForTest(transactions)

        val summary = getReportSummaryUseCase(startDate = 100L, endDate = 200L)

        assertThat(summary.totalExpenses).isEqualTo(275.5)

        assertThat(summary.largestExpense).isEqualTo(200.0)

        assertThat(summary.distinctCategoriesCount).isEqualTo(2)
    }

    @Test
    fun `sem despesas no periodo retorna vazio`() = runTest {
        val transactions = listOf(
            Transaction(4, "Presente", 80.0, TransactionType.EXPENSE, "Outros", 90L),
            Transaction(5, "Salário", 2000.0, TransactionType.INCOME, "Salário", 180L)
        )
        fakeRepository.addTransactionsForTest(transactions)

        val summary = getReportSummaryUseCase(startDate = 100L, endDate = 200L)

        assertThat(summary.totalExpenses).isEqualTo(0.0)
        assertThat(summary.largestExpense).isEqualTo(0.0)
        assertThat(summary.distinctCategoriesCount).isEqualTo(0)
    }

}