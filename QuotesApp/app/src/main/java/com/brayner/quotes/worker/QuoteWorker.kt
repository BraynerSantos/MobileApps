package com.brayner.quotes.worker

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.brayner.quotes.widget.QuoteWidget

class QuoteWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val context = applicationContext
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            ComponentName(context, QuoteWidget::class.java)
        )
        
        // Trigger update for all widgets
        for (appWidgetId in appWidgetIds) {
            QuoteWidget.updateAppWidget(context, appWidgetManager, appWidgetId)
        }

        return Result.success()
    }
}
