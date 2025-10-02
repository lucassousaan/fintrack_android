package com.example.fintrack.domain.usecases

import com.example.fintrack.data.repository.FakeTransactionRepository
import com.example.fintrack.domain.model.Transaction
import com.example.fintrack.domain.model.TransactionType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CalculateBalanceUseCaseTest {
    private lateinit var fakeRepository: FakeTransactionRepository
    private lateinit var calculateBalanceUseCase: CalculateBalanceUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeTransactionRepository()
        calculateBalanceUseCase = CalculateBalanceUseCase(fakeRepository)
    }

    @Test
    fun `calcula saldo sem transacoes e retorna zero`() = runTest {
        val balance = calculateBalanceUseCase()

        assertThat(balance).isZero()
    }

    @Test
    fun `calcula saldo com receitas e despesas e retorna a soma`() = runTest {
        val transactionsToInsert = listOf(
            Transaction(1, "Salário", 1500.0, TransactionType.INCOME, "Salário", 1759345567000),
            Transaction(2, "Aluguel", 700.0, TransactionType.EXPENSE, "Moradia", 1760123167000),
            Transaction(3, "Almoço", 56.0, TransactionType.EXPENSE, "Alimentação", 1759691167000)
        )
        fakeRepository.addTransactionsForTest(transactionsToInsert)

        val balance = calculateBalanceUseCase()

        assertThat(balance).isEqualTo(744.0)
    }

    @Test
    fun `calcula saldo apenas com receitas e retorna a soma`() = runTest {
        val transactionsToInsert = listOf(
            Transaction(1, "Salário", 1500.0, TransactionType.INCOME, "Salário", 1759345567000),
            Transaction(2, "Bonus", 700.0, TransactionType.INCOME, "Salário", 1759345567000)
        )

        fakeRepository.addTransactionsForTest(transactionsToInsert)

        val balance = calculateBalanceUseCase()

        assertThat(balance).isEqualTo(2200.0)
    }

    @Test
    fun `calcula saldo apenas com despesas e retorna a soma final`() = runTest {
        val transactionsToInsert = listOf(
            Transaction(1, "Aluguel", 700.0, TransactionType.EXPENSE, "Moradia", 1759345567000),
            Transaction(2, "Condomínio", 100.0, TransactionType.EXPENSE, "Moradia", 1759345567000)
        )

        fakeRepository.addTransactionsForTest(transactionsToInsert)

        val balance = calculateBalanceUseCase()

        assertThat(balance).isEqualTo(-800.0)
    }
}