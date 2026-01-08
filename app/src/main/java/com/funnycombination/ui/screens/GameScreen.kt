package com.funnycombination.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funnycombination.data.AppDatabase
import com.funnycombination.data.EmojiType
import com.funnycombination.navigation.Screen
import com.funnycombination.repository.HighScoreRepository
import com.funnycombination.ui.components.AnimatedBackground
import com.funnycombination.viewmodel.GameViewModel

@Composable
fun GameScreen(navController: NavController) {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val repository = remember { HighScoreRepository(database) }
    val viewModel: GameViewModel = viewModel { GameViewModel(repository) }
    
    val gameState by viewModel.gameState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.startNewGame()
    }
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedBackground()
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "Level: ${gameState.currentLevel}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            GameField(
                displayedEmoji = gameState.displayedEmoji,
                isShowingSequence = gameState.isShowingSequence,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            
            EmojiButtons(
                emojis = EmojiType.all,
                onEmojiClick = { viewModel.onEmojiClick(it) },
                enabled = gameState.isPlayerTurn && !gameState.isGameOver,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }
    }
    
    LaunchedEffect(gameState.isGameOver) {
        if (gameState.isGameOver) {
            kotlinx.coroutines.delay(1000)
            navController.navigate(Screen.GameOver.route) {
                popUpTo(Screen.Game.route) { inclusive = false }
            }
        }
    }
}

@Composable
fun GameField(
    displayedEmoji: EmojiType?,
    isShowingSequence: Boolean,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (displayedEmoji != null) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "emojiScale"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (displayedEmoji != null) 1f else 0.3f,
        animationSpec = tween(300),
        label = "emojiAlpha"
    )
    
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .size(200.dp)
                .scale(scale)
                .alpha(alpha)
                .shadow(16.dp, CircleShape),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = displayedEmoji?.emoji ?: "?",
                    fontSize = 80.sp
                )
            }
        }
    }
}

@Composable
fun EmojiButtons(
    emojis: List<EmojiType>,
    onEmojiClick: (EmojiType) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        emojis.forEach { emoji ->
            EmojiButton(
                emoji = emoji,
                onClick = { onEmojiClick(emoji) },
                enabled = enabled
            )
        }
    }
}

@Composable
fun EmojiButton(
    emoji: EmojiType,
    onClick: () -> Unit,
    enabled: Boolean
) {
    var isPressed by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.88f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "buttonPress"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.4f,
        animationSpec = tween(200),
        label = "buttonAlpha"
    )
    
    val elevation = if (isPressed) 4.dp else 12.dp
    
    Card(
        modifier = Modifier
            .size(64.dp)
            .scale(scale)
            .alpha(alpha)
            .shadow(
                elevation = elevation,
                shape = CircleShape,
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
            ),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = if (enabled) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        ),
        onClick = {
            if (enabled) {
                isPressed = true
                onClick()
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emoji.emoji,
                fontSize = 32.sp
            )
        }
    }
}

