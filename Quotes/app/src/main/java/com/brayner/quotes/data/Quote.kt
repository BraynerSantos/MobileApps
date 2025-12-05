package com.brayner.quotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true) val quoteId: Int = 0,
    val text: String,
    val author: String,
    val category: String,
    val lifeStageTarget: String, // Comma separated or specific
    val personalityTarget: String, // Comma separated MBTI types
    val genderTarget: String // Male, Female, All
)
