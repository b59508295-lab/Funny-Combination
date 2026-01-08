package com.funnycombination.ui.screens

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.funnycombination.navigation.Screen
import com.funnycombination.ui.components.AnimatedBackground

data class OnboardingPage(
    val emoji: String,
    val title: String,
    val description: String
)

@Composable
fun OnboardingScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPrefs = remember {
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }
    
    val pages = remember {
        listOf(
            OnboardingPage(
                emoji = "🎮",
                title = "Welcome to Funny Combination",
                description = "Test your memory by repeating sequences of emojis. Watch carefully and remember the pattern!"
            ),
            OnboardingPage(
                emoji = "👀",
                title = "Watch the Sequence",
                description = "Emojis will appear one by one every second. Pay attention to the order!"
            ),
            OnboardingPage(
                emoji = "🎯",
                title = "Repeat the Pattern",
                description = "After the sequence ends, tap the emojis in the same order. Each level adds one more emoji!"
            ),
            OnboardingPage(
                emoji = "🏆",
                title = "Beat Your High Score",
                description = "The game continues until you make a mistake. Try to reach the highest level possible!"
            )
        )
    }
    
    var currentPage by remember { mutableStateOf(0) }
    val infiniteTransition = rememberInfiniteTransition(label = "onboarding")
    
    val emojiScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "emojiScale"
    )
    
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
                .padding(32.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            
            Text(
                text = pages[currentPage].emoji,
                fontSize = 120.sp,
                modifier = Modifier.scale(emojiScale)
            )
            
            Text(
                text = pages[currentPage].title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Text(
                text = pages[currentPage].description,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                pages.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .size(if (index == currentPage) 12.dp else 8.dp)
                            .background(
                                color = if (index == currentPage) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                                },
                                shape = CircleShape
                            )
                    )
                }
            }
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                if (currentPage > 0) {
                    OnboardingButton(
                        text = "Previous",
                        onClick = { currentPage-- },
                        isPrimary = false,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
                
                OnboardingButton(
                    text = if (currentPage < pages.size - 1) "Next" else "Get Started",
                    onClick = {
                        if (currentPage < pages.size - 1) {
                            currentPage++
                        } else {
                            sharedPrefs.edit().putBoolean("onboarding_completed", true).apply()
                            navController.navigate(Screen.MainMenu.route) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                        }
                    },
                    isPrimary = true,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun OnboardingButton(
    text: String,
    onClick: () -> Unit,
    isPrimary: Boolean,
    modifier: Modifier = Modifier
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
        modifier = modifier
            .height(56.dp)
            .scale(scale)
            .shadow(
                elevation = if (isPressed) 4.dp else 8.dp,
                shape = RoundedCornerShape(14.dp),
                spotColor = if (isPrimary) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                } else {
                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                }
            ),
        shape = RoundedCornerShape(14.dp),
        colors = if (isPrimary) {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        } else {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        },
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
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isPrimary) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}

