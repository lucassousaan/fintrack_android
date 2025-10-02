package com.example.fintrack.domain.usecases

import com.example.fintrack.data.repository.FakeTransactionRepository
import com.example.fintrack.domain.model.Transaction
import com.example.fintrack.domain.model.TransactionType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteTransactionUseCaseTest {
    private lateinit var fakeRepository: FakeTransactionRepository
    private lateinit var deleteTransactionUseCase: DeleteTransactionUseCase

    private val transaction1 = Transaction(1, "Salgado", 10.0, TransactionType.EXPENSE, "Alimentação", 1759345567000)
    private val transaction2 = Transaction(2, "Refrigerante", 7.0, TransactionType.EXPENSE, "Alimentação", 1759345567000)
    private val transaction3 = Transaction(3, "Salário", 100.0, TransactionType.INCOME, "Salário", 1759345567000)

    @Before
    fun setUp() {
        fakeRepository = FakeTransactionRepository()
        deleteTransactionUseCase = DeleteTransactionUseCase(fakeRepository)

        fakeRepository.addTransactionsForTest(listOf(transaction1, transaction2, transaction3))
    }

    @Test
    fun `remove transacao do bd com id valido`() = runTest {
        val idToDelete = 2
        val initialSize = fakeRepository.getTransactions().size

        deleteTransactionUseCase(transactionId = idToDelete)

        val transactionsAfterDelete = fakeRepository.getTransactions()
        assertThat(transactionsAfterDelete.size).isEqualTo(initialSize - 1)
        assertThat(transactionsAfterDelete).doesNotContain(transaction2)
        assertThat(transactionsAfterDelete).contains(transaction1)
        assertThat(transactionsAfterDelete).contains(transaction3)
    }

    @Test
    fun `nao remove transacao do bd com id invalido`() = runTest {
        val idToDelete = 4
        val initialSize = fakeRepository.getTransactions().size

        deleteTransactionUseCase(transactionId = idToDelete)

        val transactionAfterDelete = fakeRepository.getTransactions()
        assertThat(transactionAfterDelete.size).isEqualTo(initialSize)
    }
}