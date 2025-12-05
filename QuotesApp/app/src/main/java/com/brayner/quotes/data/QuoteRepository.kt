package com.brayner.quotes.data

import kotlinx.coroutines.flow.Flow

class QuoteRepository(private val quoteDao: QuoteDao) {

    suspend fun saveUser(user: User) {
        quoteDao.insertUser(user)
    }

    suspend fun getUser(): User? {
        return quoteDao.getUser()
    }

    suspend fun insertQuotes(quotes: List<Quote>) {
        quoteDao.insertQuotes(quotes)
    }

    suspend fun getAllQuotes(): List<Quote> {
        return quoteDao.getAllQuotes()
    }

    suspend fun logInteraction(interaction: Interaction) {
        quoteDao.insertInteraction(interaction)
    }

    suspend fun getUserInteractions(userId: Int): List<Interaction> {
        return quoteDao.getUserInteractions(userId)
    }
    
    fun getAllInteractionsFlow(): Flow<List<Interaction>> {
        return quoteDao.getAllInteractions()
    }
}
