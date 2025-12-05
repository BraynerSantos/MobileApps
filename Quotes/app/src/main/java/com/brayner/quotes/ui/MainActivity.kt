package com.brayner.quotes.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.brayner.quotes.QuotesApplication
import com.brayner.quotes.R

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = MainViewModelFactory((application as QuotesApplication).repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        // Observe user to redirect if needed, but be careful of initial null
        // We'll rely on onStart check for the initial redirection
    }

    override fun onStart() {
        super.onStart()
        // Check user
        val repository = (application as QuotesApplication).repository
        lifecycleScope.launch {
            val user = repository.getUser()
            if (user == null) {
                startActivity(Intent(this@MainActivity, OnboardingActivity::class.java))
                finish()
            } else {
                viewModel.refreshRecommendation()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        
        val tvQuote = findViewById<TextView>(R.id.tvQuote)
        val tvAuthor = findViewById<TextView>(R.id.tvAuthor)
        val btnLike = findViewById<Button>(R.id.btnLike)
        val btnDislike = findViewById<Button>(R.id.btnDislike)
        val btnAnalytics = findViewById<Button>(R.id.btnAnalytics)

        viewModel.dailyQuote.observe(this) { quote ->
            if (quote != null) {
                tvQuote.text = "\"${quote.text}\""
                tvAuthor.text = "- ${quote.author}"
                
                btnLike.setOnClickListener {
                    viewModel.likeQuote(quote)
                    Toast.makeText(this, "Liked!", Toast.LENGTH_SHORT).show()
                    disableButtons(btnLike, btnDislike)
                }

                btnDislike.setOnClickListener {
                    viewModel.dislikeQuote(quote)
                    Toast.makeText(this, "Disliked", Toast.LENGTH_SHORT).show()
                    disableButtons(btnLike, btnDislike)
                }
                
                enableButtons(btnLike, btnDislike)
            } else {
                tvQuote.text = "No quotes available yet."
                tvAuthor.text = ""
            }
        }

        btnAnalytics.setOnClickListener {
            startActivity(Intent(this, AnalyticsActivity::class.java))
        }

        val btnSettings = findViewById<Button>(R.id.btnSettings)
        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun disableButtons(vararg buttons: View) {
        buttons.forEach { it.isEnabled = false }
    }
    
    private fun enableButtons(vararg buttons: View) {
        buttons.forEach { it.isEnabled = true }
    }
}


