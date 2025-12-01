package com.brayner.quotes.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.brayner.quotes.QuotesApplication
import com.brayner.quotes.R
import com.brayner.quotes.data.User

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val factory = MainViewModelFactory((application as QuotesApplication).repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            submitForm()
        }
    }

    private fun submitForm() {
        val etAge = findViewById<EditText>(R.id.etAge)
        val rgGender = findViewById<RadioGroup>(R.id.rgGender)
        
        val ageStr = etAge.text.toString()
        if (ageStr.isEmpty()) {
            etAge.error = "Please enter your age"
            return
        }
        val age = ageStr.toInt()

        val genderId = rgGender.checkedRadioButtonId
        if (genderId == -1) {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show()
            return
        }
        val gender = findViewById<RadioButton>(genderId).text.toString()

        // Calculate MBTI
        val mbti = calculateMBTI()
        if (mbti == null) {
            Toast.makeText(this, "Please answer all quiz questions", Toast.LENGTH_SHORT).show()
            return
        }

        // Calculate Life Stage
        val lifeStage = when {
            age < 20 -> "Teen"
            age in 20..35 -> "Young Adult"
            age in 36..60 -> "Adult"
            else -> "Senior"
        }

        val user = User(
            age = age,
            lifeStage = lifeStage,
            gender = gender,
            mbtiType = mbti
        )

        viewModel.saveUser(user)
        
        // Navigate to Main
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun calculateMBTI(): String? {
        val q1 = findViewById<RadioGroup>(R.id.rgQ1).checkedRadioButtonId
        val q2 = findViewById<RadioGroup>(R.id.rgQ2).checkedRadioButtonId
        val q3 = findViewById<RadioGroup>(R.id.rgQ3).checkedRadioButtonId
        val q4 = findViewById<RadioGroup>(R.id.rgQ4).checkedRadioButtonId

        if (q1 == -1 || q2 == -1 || q3 == -1 || q4 == -1) return null

        val ie = if (q1 == R.id.rbQ1E) "E" else "I"
        val sn = if (q2 == R.id.rbQ2S) "S" else "N"
        val tf = if (q3 == R.id.rbQ3T) "T" else "F"
        val jp = if (q4 == R.id.rbQ4J) "J" else "P"

        return "$ie$sn$tf$jp"
    }
}
