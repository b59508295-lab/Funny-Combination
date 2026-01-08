package com.funnycombination.repository

import com.funnycombination.data.AppDatabase
import com.funnycombination.data.HighScore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HighScoreRepository(private val database: AppDatabase) {
    suspend fun getAllSorted(): List<HighScore> {
        return database.highScoreDao().getAllSorted()
    }
    
    suspend fun getBest(): HighScore? {
        return database.highScoreDao().getBest()
    }
    
    suspend fun insert(highScore: HighScore) {
        database.highScoreDao().insert(highScore)
    }
    
    suspend fun shouldSaveScore(sequenceLength: Int): Boolean {
        val best = getBest()
        return best == null || sequenceLength > best.sequenceLength
    }
}

