package com.brayner.quotes.utils

import com.brayner.quotes.data.Interaction
import com.brayner.quotes.data.Quote
import com.brayner.quotes.data.User

object RecommendationEngine {

    fun recommendQuote(
        user: User,
        quotes: List<Quote>,
        interactions: List<Interaction>
    ): Quote? {
        if (quotes.isEmpty()) return null

        val scoredQuotes = quotes.map { quote ->
            val score = calculateScore(user, quote, interactions)
            quote to score
        }

        // Return the quote with the highest score
        // Shuffle to add randomness if scores are equal
        return scoredQuotes.shuffled().maxByOrNull { it.second }?.first
    }

    private fun calculateScore(user: User, quote: Quote, interactions: List<Interaction>): Int {
        var score = 0

        // 1. Demographic Match
        if (quote.lifeStageTarget.contains(user.lifeStage, ignoreCase = true) || quote.lifeStageTarget.equals("All", ignoreCase = true)) {
            score += 3
        }

        if (quote.genderTarget.equals(user.gender, ignoreCase = true) || quote.genderTarget.equals("All", ignoreCase = true)) {
            score += 2
        }

        if (quote.personalityTarget.contains(user.mbtiType, ignoreCase = true)) {
            score += 5
        }

        // 2. Interactions
        // Check if user has interacted with this specific quote
        val interaction = interactions.find { it.quoteId == quote.quoteId }
        if (interaction != null) {
            if (interaction.liked) {
                score += 10
            } else {
                score -= 10
            }
        }

        // 3. Category preference (Optional enhancement based on PRD "Most liked quote categories")
        // If user liked other quotes in this category, boost score
        // For MVP, we'll skip this complex join or assume we can pass a map of preferences.
        // Let's keep it simple for now as per PRD "Base score = demographic match + Like/Dislike"

        return score
    }
}
