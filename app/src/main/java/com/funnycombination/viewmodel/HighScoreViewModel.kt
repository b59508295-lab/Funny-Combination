package com.funnycombination.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funnycombination.data.HighScore
import com.funnycombination.repository.HighScoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HighScoreViewModel(
    private val repository: HighScoreRepository
) : ViewModel() {
    
    private val _highScores = MutableStateFlow<List<HighScore>>(emptyList())
    val highScores: StateFlow<List<HighScore>> = _highScores.asStateFlow()
    
    init {
        loadHighScores()
    }
    
    fun loadHighScores() {
        viewModelScope.launch {
            _highScores.value = repository.getAllSorted()
        }
    }
}

