package com.brayner.quotes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    // User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getUser(): User?

    // Quotes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(quotes: List<Quote>)

    @Query("SELECT * FROM quotes")
    suspend fun getAllQuotes(): List<Quote>

    @Query("SELECT * FROM quotes WHERE quoteId = :id")
    suspend fun getQuoteById(id: Int): Quote?

    // Interactions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInteraction(interaction: Interaction)

    @Query("SELECT * FROM interactions WHERE userId = :userId")
    suspend fun getUserInteractions(userId: Int): List<Interaction>
    
    @Query("SELECT * FROM interactions")
    fun getAllInteractions(): Flow<List<Interaction>>
}
