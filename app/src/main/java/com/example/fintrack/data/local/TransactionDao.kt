package com.example.fintrack.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fintrack.data.models.CategorySpendingDto
import com.example.fintrack.data.models.TransactionModel

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions(): List<TransactionModel>

    @Insert
    suspend fun insert(transaction: TransactionModel)

    @Query("DELETE FROM transactions WHERE id = :transactionId")
    suspend fun delete(transactionId: Int)

    @Query("SELECT * FROM transactions ORDER BY date DESC LIMIT 5")
    suspend fun getLastFiveTransactions(): List<TransactionModel>

    @Query("""
        SELECT category, SUM(amount) as totalAmount
        FROM transactions
        WHERE type = 'EXPENSE' AND date BETWEEN :startDate AND :endDate
        GROUP BY category
    """)
    suspend fun getSpendingByCategory(startDate: Long, endDate: Long): List<CategorySpendingDto>

}