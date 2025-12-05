package com.brayner.quotes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.brayner.quotes.data.Interaction
import com.brayner.quotes.data.Quote
import com.brayner.quotes.data.QuoteRepository
import com.brayner.quotes.data.User
import com.brayner.quotes.utils.RecommendationEngine
import kotlinx.coroutines.launch

class MainViewModel(private val repository: QuoteRepository) : ViewModel() {

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _dailyQuote = MutableLiveData<Quote?>()
    val dailyQuote: LiveData<Quote?> = _dailyQuote

    val allInteractions: LiveData<List<Interaction>> = repository.getAllInteractionsFlow().asLiveData()

    fun checkUser() {
        viewModelScope.launch {
            _currentUser.value = repository.getUser()
        }
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            repository.saveUser(user)
            _currentUser.value = user
            // After saving user, load quotes if empty and recommend one
            loadInitialQuotes()
        }
    }

    private fun loadInitialQuotes() {
        viewModelScope.launch {
            val quotes = repository.getAllQuotes()
            if (quotes.isEmpty()) {
                // Seed data if empty
                val seedQuotes = getSeedQuotes()
                repository.insertQuotes(seedQuotes)
                refreshRecommendation()
            } else {
                refreshRecommendation()
            }
        }
    }

    fun refreshRecommendation() {
        viewModelScope.launch {
            val user = repository.getUser() ?: return@launch
            val quotes = repository.getAllQuotes()
            val interactions = repository.getUserInteractions(user.userId)
            
            val recommended = RecommendationEngine.recommendQuote(user, quotes, interactions)
            _dailyQuote.value = recommended
        }
    }

    fun likeQuote(quote: Quote) {
        logInteraction(quote, true)
    }

    fun dislikeQuote(quote: Quote) {
        logInteraction(quote, false)
    }

    private fun logInteraction(quote: Quote, liked: Boolean) {
        viewModelScope.launch {
            val user = _currentUser.value ?: return@launch
            val interaction = Interaction(
                userId = user.userId,
                quoteId = quote.quoteId,
                liked = liked,
                timestamp = System.currentTimeMillis()
            )
            repository.logInteraction(interaction)
            // Optionally refresh recommendation immediately or keep the same one for the day
            // PRD says "Highest scoring quote -> recommended". If we dislike, score drops, maybe new one appears?
            // "Daily Quote Feed: Display one daily quote". Usually implies it stays for the day.
            // But "Like/dislike buttons to improve recommendations" implies future recommendations.
            // Let's keep the current quote but update the backend.
        }
    }

    private fun getSeedQuotes(): List<Quote> {
        // Basic seed data
        return listOf(
            Quote(text = "The only way to do great work is to love what you do.", author = "Steve Jobs", category = "Motivation", lifeStageTarget = "All", personalityTarget = "ENTJ,INTJ", genderTarget = "All"),
            Quote(text = "Life is what happens when you're busy making other plans.", author = "John Lennon", category = "Life", lifeStageTarget = "Adult,Senior", personalityTarget = "INFP,ENFP", genderTarget = "All"),
            Quote(text = "Get busy living or get busy dying.", author = "Stephen King", category = "Motivation", lifeStageTarget = "Young Adult,Adult", personalityTarget = "ESTP,ISTP", genderTarget = "Male"),
            Quote(text = "You only live once, but if you do it right, once is enough.", author = "Mae West", category = "Life", lifeStageTarget = "Teen,Young Adult", personalityTarget = "ESFP,ISFP", genderTarget = "Female"),
            Quote(text = "In the end, we will remember not the words of our enemies, but the silence of our friends.", author = "Martin Luther King Jr.", category = "Reflection", lifeStageTarget = "Adult,Senior", personalityTarget = "INFJ,ENFJ", genderTarget = "All")
        )
    }
}

class MainViewModelFactory(private val repository: QuoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
