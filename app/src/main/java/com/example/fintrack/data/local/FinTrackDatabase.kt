package com.example.fintrack.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fintrack.data.models.TransactionModel

@Database(entities = [TransactionModel::class], version = 1)
abstract class FinTrackDatabase: RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}
