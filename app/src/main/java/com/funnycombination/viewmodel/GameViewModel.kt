package com.funnycombination.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funnycombination.data.EmojiType
import com.funnycombination.data.HighScore
import com.funnycombination.repository.HighScoreRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GameState(
    val currentSequence: List<EmojiType> = emptyList(),
    val playerSequence: List<EmojiType> = emptyList(),
    val currentLevel: Int = 1,
    val isShowingSequence: Boolean = false,
    val isGameOver: Boolean = false,
    val isPlayerTurn: Boolean = false,
    val displayedEmoji: EmojiType? = null,
    val score: Int = 0,
    val completedSequenceLength: Int = 0
)

class GameViewModel(
    private val repository: HighScoreRepository
) : ViewModel() {
    
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()
    
    private val _isNewHighScore = MutableStateFlow(false)
    val isNewHighScore: StateFlow<Boolean> = _isNewHighScore.asStateFlow()
    
    fun startNewGame() {
        _gameState.value = GameState()
        _isNewHighScore.value = false
        generateNextSequence()
    }
    
    private fun generateNextSequence() {
        val newSequence = _gameState.value.currentSequence + EmojiType.all.random()
        _gameState.value = _gameState.value.copy(
            currentSequence = newSequence,
            playerSequence = emptyList(),
            isPlayerTurn = false,
            displayedEmoji = null
        )
        showSequence()
    }
    
    private fun showSequence() {
        viewModelScope.launch {
            _gameState.value = _gameState.value.copy(isShowingSequence = true)
            
            for (emoji in _gameState.value.currentSequence) {
                _gameState.value = _gameState.value.copy(displayedEmoji = emoji)
                delay(1000)
                _gameState.value = _gameState.value.copy(displayedEmoji = null)
                delay(200)
            }
            
            _gameState.value = _gameState.value.copy(
                isShowingSequence = false,
                isPlayerTurn = true
            )
        }
    }
    
    fun onEmojiClick(emoji: EmojiType) {
        if (!_gameState.value.isPlayerTurn || _gameState.value.isGameOver) return
        
        val newPlayerSequence = _gameState.value.playerSequence + emoji
        _gameState.value = _gameState.value.copy(
            playerSequence = newPlayerSequence,
            displayedEmoji = emoji
        )
        
        viewModelScope.launch {
            delay(300)
            _gameState.value = _gameState.value.copy(displayedEmoji = null)
            
            val currentSequence = _gameState.value.currentSequence
            if (newPlayerSequence.size <= currentSequence.size) {
                val index = newPlayerSequence.size - 1
                if (newPlayerSequence[index] != currentSequence[index]) {
                    gameOver()
                } else if (newPlayerSequence.size == currentSequence.size) {
                    levelComplete()
                }
            }
        }
    }
    
    private fun levelComplete() {
        viewModelScope.launch {
            val completedLength = _gameState.value.currentSequence.size
            _gameState.value = _gameState.value.copy(
                currentLevel = _gameState.value.currentLevel + 1,
                score = _gameState.value.currentLevel,
                completedSequenceLength = completedLength
            )
            delay(500)
            generateNextSequence()
        }
    }
    
    private fun gameOver() {
        viewModelScope.launch {
            _gameState.value = _gameState.value.copy(
                isGameOver = true,
                isPlayerTurn = false
            )
            
            val sequenceLength = _gameState.value.completedSequenceLength
            if (sequenceLength > 0) {
                val highScore = HighScore.create(sequenceLength)
                repository.insert(highScore)
                
                val best = repository.getBest()
                val isNewBest = best != null && best.sequenceLength == sequenceLength && best.date == highScore.date
                _isNewHighScore.value = isNewBest
            }
        }
    }
    
    fun resetGame() {
        startNewGame()
    }
}

