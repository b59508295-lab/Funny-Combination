package com.funnycombination.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity(tableName = "high_scores")
data class HighScore(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,
    val sequenceLength: Int
) {
    companion object {
        private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        
        fun create(sequenceLength: Int): HighScore {
            return HighScore(
                date = LocalDate.now().format(dateFormatter),
                sequenceLength = sequenceLength
            )
        }
    }
}

