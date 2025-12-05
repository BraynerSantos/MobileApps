package com.brayner.quotes.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.brayner.quotes.QuotesApplication
import com.brayner.quotes.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class AnalyticsActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)

        val factory = MainViewModelFactory((application as QuotesApplication).repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        val chart = findViewById<PieChart>(R.id.chart)
        val tvInsights = findViewById<TextView>(R.id.tvInsights)

        viewModel.allInteractions.observe(this) { interactions ->
            if (interactions.isEmpty()) {
                tvInsights.text = "No interactions yet. Like or dislike quotes to see insights!"
                return@observe
            }

            val likedCount = interactions.count { it.liked }
            val dislikedCount = interactions.count { !it.liked }

            val entries = listOf(
                PieEntry(likedCount.toFloat(), "Liked"),
                PieEntry(dislikedCount.toFloat(), "Disliked")
            )

            val dataSet = PieDataSet(entries, "Interactions")
            dataSet.colors = listOf(Color.GREEN, Color.RED)
            dataSet.valueTextSize = 14f
            
            val data = PieData(dataSet)
            chart.data = data
            chart.description.isEnabled = false
            chart.invalidate()

            // Insights
            viewModel.currentUser.observe(this) { user ->
                if (user != null) {
                    tvInsights.text = "You have interacted with ${interactions.size} quotes.\n" +
                            "Personality Type: ${user.mbtiType}\n" +
                            "Life Stage: ${user.lifeStage}"
                }
            }
        }
    }
}
