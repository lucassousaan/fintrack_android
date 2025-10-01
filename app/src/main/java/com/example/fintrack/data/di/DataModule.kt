package com.example.fintrack.data.di

import android.content.Context
import androidx.room.Room
import com.example.fintrack.data.local.FinTrackDatabase
import com.example.fintrack.data.local.TransactionDao
import com.example.fintrack.data.repository.TransactionRepositoryImpl
import com.example.fintrack.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(appContext: Context): FinTrackDatabase {
        return Room.databaseBuilder(
            appContext,
            FinTrackDatabase::class.java,
            "fintrack_db"
        ).build()
    }

    @Provides
    fun provideTransactionDao(db: FinTrackDatabase): TransactionDao {
        return db.transactionDao()
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(
        dao: TransactionDao
    ): TransactionRepository {
        return TransactionRepositoryImpl(dao)
    }

}