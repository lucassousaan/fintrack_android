package com.example.fintrack.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.fintrack.data.models.CategorySpendingDto
import com.example.fintrack.data.models.TransactionModel
import com.example.fintrack.domain.model.TransactionType

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

    @RawQuery
    suspend fun filter(query: SimpleSQLiteQuery): List<TransactionModel>

    suspend fun filterTransactions(
        query: String? = null,
        type: TransactionType? = null,
        categoryName: String? = null,
        startDate: Long? = null,
        endDate: Long? = null,
    ): List<TransactionModel> {
        val sb = StringBuilder("SELECT * FROM transactions")
        val args = mutableListOf<Any>()
        val conditions = mutableListOf<String>()

        query?.let {
            if (it.isNotBlank()) {
                conditions.add("description LIKE ?")
                args.add("%$it")
            }
        }

        type?.let {
            conditions.add("type = ?")
            args.add(it.name)
        }

        categoryName?.let {
            conditions.add("category = ?")
            args.add(it)
        }

        if (startDate != null && endDate != null) {
            conditions.add("date BETWENN ? AND ?")
            args.add(startDate)
            args.add(endDate)
        }

        if (conditions.isNotEmpty()) {
            sb.append(" WHERE ").append(conditions.joinToString(" AND "))
        }

        sb.append(" ORDER BY date DESC")

        return filter(query = SimpleSQLiteQuery(sb.toString(), args.toTypedArray()))
    }

    @Query("""
        SELECT COALESCE(SUM(amount), 0.0) FROM transactions
        WHERE type = 'EXPENSE' AND date BETWEEN :startDate AND :endDate
    """)
    suspend fun getTotalExpenses(startDate: Long, endDate: Long): Double

    @Query("""
        SELECT COALESCE(MAX(amount), 0.0) FROM transactions 
        WHERE type = 'EXPENSE' AND date BETWEEN :startDate AND :endDate
    """)
    suspend fun getLargestExpense(startDate: Long, endDate: Long): Double

    @Query("""
        SELECT COUNT(DISTINCT category) FROM transactions 
        WHERE type = 'EXPENSE' AND date BETWEEN :startDate AND :endDate
    """)
    suspend fun getDistinctCategoriesCount(startDate: Long, endDate: Long): Int

}