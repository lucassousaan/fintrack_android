package com.example.fintrack.domain.usecases

import com.example.fintrack.data.repository.FakeTransactionRepository
import com.example.fintrack.domain.model.Transaction
import com.example.fintrack.domain.model.TransactionType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetSpendingByCategoryUseCaseTest {
    private lateinit var fakeRepository: FakeTransactionRepository
    private lateinit var getSpendingByCategoryUseCase: GetSpendingByCategoryUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeTransactionRepository()
        getSpendingByCategoryUseCase = GetSpendingByCategoryUseCase(fakeRepository)
    }

    @Test
    fun `despesas no periodo deve retornar gastos agregados por categoria`() = runTest {
        val transactions = listOf(
            Transaction(1, "Almoço", 30.0, TransactionType.EXPENSE, "Alimentação", 150L),
            Transaction(2, "Supermercado", 120.0, TransactionType.EXPENSE, "Alimentação", 160L),
            Transaction(3, "Ônibus", 4.5, TransactionType.EXPENSE, "Transporte", 170L),

            Transaction(4, "Cinema", 50.0, TransactionType.EXPENSE, "Lazer", 90L),

            Transaction(5, "Salário", 2000.0, TransactionType.INCOME, "Salário", 180L)
        )
        fakeRepository.addTransactionsForTest(transactions)

        val result = getSpendingByCategoryUseCase(startDate = 100L, endDate = 200L)

        assertThat(result).hasSize(2)

        val alimentacaoSpending = result.find { it.categoryName == "Alimentação" }
        assertThat(alimentacaoSpending).isNotNull()
        assertThat(alimentacaoSpending?.totalAmount).isEqualTo(150.0)

        val transporteSpending = result.find { it.categoryName == "Transporte" }
        assertThat(transporteSpending).isNotNull()
        assertThat(transporteSpending?.totalAmount).isEqualTo(4.5)
    }

    @Test
    fun `nenhuma despesa no periodo, retorna lista vazia`() = runTest {
        val transactions = listOf(
            Transaction(4, "Cinema", 50.0, TransactionType.EXPENSE, "Lazer", 90L),
            Transaction(5, "Salário", 2000.0, TransactionType.INCOME, "Salário", 180L)
        )
        fakeRepository.addTransactionsForTest(transactions)

        val result = getSpendingByCategoryUseCase(startDate = 100L, endDate = 200L)

        assertThat(result).isEmpty()
    }
}