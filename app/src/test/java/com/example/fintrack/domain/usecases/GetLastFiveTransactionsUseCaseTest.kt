package com.example.fintrack.domain.usecases

import com.example.fintrack.data.repository.FakeTransactionRepository
import com.example.fintrack.domain.model.Transaction
import com.example.fintrack.domain.model.TransactionType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetLastFiveTransactionsUseCaseTest {

    private lateinit var fakeRepository: FakeTransactionRepository
    private lateinit var getLastFiveTransactionsUseCase: GetLastFiveTransactionsUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeTransactionRepository()
        getLastFiveTransactionsUseCase = GetLastFiveTransactionsUseCase(fakeRepository)
    }

    @Test
    fun `quando ha mais de 5 transacoes, retorna apenas as 5 mais recentes e ordenadas`() = runTest {
        val transactions = listOf(
            Transaction(1, "T1", 10.0, TransactionType.EXPENSE, "Categoria", 500L),
            Transaction(2, "T2", 10.0, TransactionType.EXPENSE, "Categoria", 300L),
            Transaction(3, "T3", 10.0, TransactionType.EXPENSE, "Categoria", 200L),
            Transaction(4, "T4", 10.0, TransactionType.EXPENSE, "Categoria", 400L),
            Transaction(5, "T5", 10.0, TransactionType.EXPENSE, "Categoria", 100L),
            Transaction(6, "T6", 10.0, TransactionType.EXPENSE, "Categoria", 600L)
        )
        fakeRepository.addTransactionsForTest(transactions)

        val result = getLastFiveTransactionsUseCase()

        assertThat(result.size).isEqualTo(5)
        assertThat(result.first().id).isEqualTo(6)
        assertThat(result.last().id).isEqualTo(3)
    }

    @Test
    fun `quando ha menos de 5 transacoes retorna todas ordenadas`() = runTest {
        val transactions = listOf(
            Transaction(1, "T1", 10.0, TransactionType.EXPENSE, "Categoria", 300L),
            Transaction(2, "T2", 10.0, TransactionType.EXPENSE, "Categoria", 100L),
            Transaction(3, "T3", 10.0, TransactionType.EXPENSE, "Categoria", 200L)
        )
        fakeRepository.addTransactionsForTest(transactions)

        val result = getLastFiveTransactionsUseCase()

        assertThat(result.size).isEqualTo(3)
        assertThat(result.first().id).isEqualTo(1)
        assertThat(result.last().id).isEqualTo(2)
    }

    @Test
    fun `quando nao ha transacoes retorna uma lista vazia`() = runTest {
        val result = getLastFiveTransactionsUseCase()

        assertThat(result).isEmpty()
    }
}