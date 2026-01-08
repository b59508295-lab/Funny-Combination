package com.funnycombination.ui.screens

import android.app.Activity
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
import androidx.navigation.NavController
import com.funnycombination.navigation.Screen
import com.funnycombination.ui.components.AnimatedBackground

@Composable
fun MainMenuScreen(navController: NavController) {
    val context = LocalContext.current
    val infiniteTransition = rememberInfiniteTransition(label = "menu")
    
    val buttonScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "buttonScale"
    )
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedBackground()
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 32.dp)
        ) {
            Spacer(modifier = Modifier.weight(0.3f))
            
            Text(
                text = "Funny Combination",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            Text(
                text = "😊⭐❤️🔥🚀",
                fontSize = 48.sp,
                modifier = Modifier.padding(bottom = 48.dp)
            )
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                MenuButton(
                    text = "Play",
                    onClick = { navController.navigate(Screen.Game.route) },
                    modifier = Modifier.scale(buttonScale)
                )
                
                MenuButton(
                    text = "High Score",
                    onClick = { navController.navigate(Screen.HighScore.route) }
                )
                
                MenuButton(
                    text = "Privacy Policy",
                    onClick = { navController.navigate(Screen.PrivacyPolicy.route) }
                )
                
                MenuButton(
                    text = "Exit",
                    onClick = {
                        (context as? Activity)?.finish()
                    }
                )
            }
            
            Spacer(modifier = Modifier.weight(0.3f))
        }
    }
}

@Composable
fun MenuButton(
    text: String,
    onClick: () -> Unit,
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
            .fillMaxWidth(0.75f)
            .height(64.dp)
            .scale(scale)
            .shadow(
                elevation = if (isPressed) 4.dp else 8.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
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
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

