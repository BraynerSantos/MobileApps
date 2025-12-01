package com.brayner.quotes.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.brayner.quotes.R
import com.brayner.quotes.data.AppDatabase
import com.brayner.quotes.data.QuoteRepository
import com.brayner.quotes.ui.MainActivity
import com.brayner.quotes.utils.RecommendationEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuoteWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                val database = AppDatabase.getDatabase(context)
                val repository = QuoteRepository(database.quoteDao())
                val user = repository.getUser()
                val quoteText = if (user != null) {
                    val quotes = repository.getAllQuotes()
                    val interactions = repository.getUserInteractions(user.userId)
                    val recommended = RecommendationEngine.recommendQuote(user, quotes, interactions)
                    recommended?.text ?: "No quotes available"
                } else {
                    "Open app to set up"
                }

                val views = RemoteViews(context.packageName, R.layout.widget_quote)
                views.setTextViewText(R.id.appwidget_text, quoteText)

                // Click to open app
                val intent = Intent(context, MainActivity::class.java)
                val pendingIntent = PendingIntent.getActivity(
                    context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)

                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
        }
    }
}
