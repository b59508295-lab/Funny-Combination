package com.funnycombination.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.funnycombination.data.AppDatabase
import com.funnycombination.navigation.Screen
import com.funnycombination.repository.HighScoreRepository
import com.funnycombination.ui.components.AnimatedBackground
import com.funnycombination.viewmodel.GameViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun GameOverScreen(navController: NavController) {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val repository = remember { HighScoreRepository(database) }
    val viewModel: GameViewModel = viewModel { GameViewModel(repository) }
    
    val gameState by viewModel.gameState.collectAsState()
    val isNewHighScore by viewModel.isNewHighScore.collectAsState()
    
    var bestScore by remember { mutableStateOf<Int?>(null) }
    
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val best = repository.getBest()
            bestScore = best?.sequenceLength
        }
    }
    
    val infiniteTransition = rememberInfiniteTransition(label = "gameOver")
    
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedBackground()
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "Game Over",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
            
            if (isNewHighScore) {
                Card(
                    modifier = Modifier
                        .scale(pulseScale)
                        .padding(vertical = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = "🎉 New High Score! 🎉",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            

            Text(
                text = "Level: ${gameState.currentLevel}",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            
            if (bestScore != null) {
                Card(
                    modifier = Modifier.padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f)
                    )
                ) {
                    Text(
                        text = "Best Score: $bestScore",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                GameOverButton(
                    text = "Play Again",
                    onClick = {
                        viewModel.resetGame()
                        navController.navigate(Screen.Game.route) {
                            popUpTo(Screen.GameOver.route) { inclusive = true }
                        }
                    },
                    isPrimary = true
                )
                
                GameOverButton(
                    text = "Main Menu",
                    onClick = {
                        navController.navigate(Screen.MainMenu.route) {
                            popUpTo(Screen.Game.route) { inclusive = true }
                        }
                    },
                    isPrimary = false
                )
            }
        }
    }
}

@Composable
fun GameOverButton(
    text: String,
    onClick: () -> Unit,
    isPrimary: Boolean
) {
    var isPressed by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "buttonPress"
    )
    
    Card(
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .height(64.dp)
            .scale(scale)
            .shadow(
                elevation = if (isPressed) 4.dp else 10.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = if (isPrimary) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                } else {
                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
                }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isPrimary) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.secondary
            }
        ),
        onClick = {
            isPressed = true
            onClick()
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isPrimary) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSecondary
                }
            )
        }
    }
}

