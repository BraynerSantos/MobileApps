package com.brayner.quotes.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "interactions",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Quote::class,
            parentColumns = ["quoteId"],
            childColumns = ["quoteId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        androidx.room.Index(value = ["userId"]),
        androidx.room.Index(value = ["quoteId"])
    ]
)
data class Interaction(
    @PrimaryKey(autoGenerate = true) val interactionId: Int = 0,
    val userId: Int,
    val quoteId: Int,
    val liked: Boolean,
    val timestamp: Long
)
