package com.example.fintrack.data.mappers

import com.example.fintrack.data.models.TransactionModel
import com.example.fintrack.domain.model.Transaction
import com.example.fintrack.domain.model.TransactionType
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TransactionMappersTest {

    @Test
    fun `mapeia corretamente data to domain`() {
        val transactionData = TransactionModel(
            id = 1,
            description = "Teste",
            amount = 100.0,
            type = "INCOME",
            category = "TesteCat",
            date = System.currentTimeMillis()
        )

        val transactionDomain = transactionData.toDomain()

        assertThat(transactionDomain.id).isEqualTo(transactionData.id)
        assertThat(transactionDomain.description).isEqualTo(transactionData.description)
        assertThat(transactionDomain.amount).isEqualTo(transactionData.amount)
        assertThat(transactionDomain.type).isEqualTo(TransactionType.INCOME)
        assertThat(transactionDomain.category).isEqualTo(transactionData.category)
        assertThat(transactionDomain.date).isEqualTo(transactionData.date)
    }

    @Test
    fun `mapeia corretamente domain to data`() {
        val transactionDomain = Transaction(
            id = 1,
            description = "Teste",
            amount = 100.0,
            type = TransactionType.INCOME,
            category = "TesteCat",
            date = System.currentTimeMillis()
        )

        val transactionData = transactionDomain.toModel()

        assertThat(transactionData.id).isEqualTo(transactionDomain.id)
        assertThat(transactionData.description).isEqualTo(transactionDomain.description)
        assertThat(transactionData.amount).isEqualTo(transactionDomain.amount)
        assertThat(transactionData.type).isEqualTo("INCOME")
        assertThat(transactionData.category).isEqualTo(transactionDomain.category)
        assertThat(transactionData.date).isEqualTo(transactionDomain.date)
    }

}