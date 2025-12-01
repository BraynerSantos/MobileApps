package com.brayner.quotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val age: Int,
    val lifeStage: String, // Teen, Young Adult, Adult, Senior
    val gender: String, // Male, Female, Other, Prefer Not To Say
    val mbtiType: String // INTJ, ESFP, etc.
)
