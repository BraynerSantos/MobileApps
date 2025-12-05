package com.brayner.quotes

import android.app.Application
import com.brayner.quotes.data.AppDatabase
import com.brayner.quotes.data.QuoteRepository

class QuotesApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { QuoteRepository(database.quoteDao()) }

    override fun onCreate() {
        super.onCreate()
        setupWorkManager()
    }

    private fun setupWorkManager() {
        val workRequest = androidx.work.PeriodicWorkRequestBuilder<com.brayner.quotes.worker.QuoteWorker>(
            24, java.util.concurrent.TimeUnit.HOURS
        ).build()

        androidx.work.WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DailyQuoteUpdate",
            androidx.work.ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
