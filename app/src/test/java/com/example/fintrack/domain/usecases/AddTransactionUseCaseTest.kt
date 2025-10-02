package com.example.fintrack.domain.usecases

import com.example.fintrack.data.repository.FakeTransactionRepository
import com.example.fintrack.domain.exceptions.InvalidTransactionException
import com.example.fintrack.domain.model.Transaction
import com.example.fintrack.domain.model.TransactionType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddTransactionUseCaseTest {
    private lateinit var fakeRepository: FakeTransactionRepository
    private lateinit var addTransactionUseCase: AddTransactionUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeTransactionRepository()
        addTransactionUseCase = AddTransactionUseCase(fakeRepository)
    }

    @Test
    fun `deve adicionar transacao ao bd`() = runTest {
        val newTransaction = Transaction(1, "Freela", 500.0, TransactionType.INCOME, "Trabalho", System.currentTimeMillis())

        val initialTransactions = fakeRepository.getTransactions()
        assertThat(initialTransactions).isEmpty()

        addTransactionUseCase(newTransaction)

        val transactionAfterAdd = fakeRepository.getTransactions()
        assertThat(transactionAfterAdd.size).isEqualTo(1)
        assertThat(transactionAfterAdd).contains(newTransaction)
    }

    @Test(expected = InvalidTransactionException::class)
    fun `amount com valor zero deve lancar uma excecao`() = runTest {
        val invalidTransaction = Transaction(1, "Invalida", 0.0, TransactionType.INCOME, "Categoria", System.currentTimeMillis())

        addTransactionUseCase(invalidTransaction)
    }

    @Test(expected = InvalidTransactionException::class)
    fun `descricao em branco deve lancar uma excecao`() = runTest {
        val invalidTransaction = Transaction(1, "  ", 100.0, TransactionType.INCOME, "Categoria", System.currentTimeMillis())

        addTransactionUseCase(invalidTransaction)
    }
}