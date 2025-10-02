package com.example.fintrack.domain.usecases

import com.example.fintrack.data.repository.FakeTransactionRepository
import com.example.fintrack.domain.model.Transaction
import com.example.fintrack.domain.model.TransactionType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FilterTransactionsUseCaseTest {
    private lateinit var fakeRepository: FakeTransactionRepository
    private lateinit var filterTransactionsUseCase: FilterTransactionsUseCase

    private val transaction1 = Transaction(1, "Salário de Outubro", 2000.0, TransactionType.INCOME, "Salário", 100L)
    private val transaction2 = Transaction(2, "Aluguel", 800.0, TransactionType.EXPENSE, "Moradia", 110L)
    private val transaction3 = Transaction(3, "Supermercado", 300.0, TransactionType.EXPENSE, "Alimentação", 120L)
    private val transaction4 = Transaction(4, "Adiantamento Salário", 500.0, TransactionType.INCOME, "Salário", 130L)

    @Before
    fun setUp() {
        fakeRepository = FakeTransactionRepository()
        filterTransactionsUseCase = FilterTransactionsUseCase(fakeRepository)
        fakeRepository.addTransactionsForTest(listOf(transaction1, transaction2, transaction3, transaction4))
    }

    @Test
    fun `sem filtros retorna todas as transacoes ordenadas`() = runTest {
        val result = filterTransactionsUseCase()
        assertThat(result).hasSize(4)
        assertThat(result.first()).isEqualTo(transaction4)
    }

    @Test
    fun `filtra por descricao retorna correspondentes`() = runTest {
        val result = filterTransactionsUseCase(query = "Salário")
        assertThat(result).hasSize(2)
        assertThat(result).containsExactly(transaction4, transaction1)
    }

    @Test
    fun `filtra por tipo, retorna correspondentes`() = runTest {
        val result = filterTransactionsUseCase(type = TransactionType.EXPENSE)
        assertThat(result).hasSize(2)
        assertThat(result).containsExactly(transaction3, transaction2)
    }

    @Test
    fun `filtra por categoria, retorna correspondentes`() = runTest {
        val result = filterTransactionsUseCase(categoryName = "Moradia")
        assertThat(result).hasSize(1)
        assertThat(result.first()).isEqualTo(transaction2)
    }

    @Test
    fun `combina descricao e tipo e retorna correspondentes`() = runTest {
        val result = filterTransactionsUseCase(query = "mercado", type = TransactionType.EXPENSE)
        assertThat(result).hasSize(1)
        assertThat(result.first()).isEqualTo(transaction3)
    }

    @Test
    fun `descricao invalida nao retorna nada`() = runTest {
        val result = filterTransactionsUseCase(query = "investimentos")
        assertThat(result).isEmpty()
    }
}