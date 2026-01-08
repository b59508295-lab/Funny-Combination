package com.funnycombination.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HighScoreDao {
    @Query("SELECT * FROM high_scores ORDER BY sequenceLength DESC")
    suspend fun getAllSorted(): List<HighScore>
    
    @Query("SELECT * FROM high_scores ORDER BY sequenceLength DESC LIMIT 1")
    suspend fun getBest(): HighScore?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(highScore: HighScore): Long
    
    @Query("SELECT COUNT(*) FROM high_scores")
    suspend fun getCount(): Int
}

