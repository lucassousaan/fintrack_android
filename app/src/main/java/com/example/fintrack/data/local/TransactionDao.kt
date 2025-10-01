package com.example.fintrack.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fintrack.data.models.TransactionModel

@Dao
interface TransactionDao {

    @Query("SELECT * from transactions")
    suspend fun getAllTransactions(): List<TransactionModel>

    @Insert
    suspend fun insert(transaction: TransactionModel)

    @Query("DELETE FROM transactions WHERE id = :transactionId")
    suspend fun delete(transactionId: Int)

}