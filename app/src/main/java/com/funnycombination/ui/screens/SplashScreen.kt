package com.funnycombination.ui.screens

import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.funnycombination.navigation.Screen
import com.funnycombination.ui.components.AnimatedBackground
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPrefs = remember {
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }
    
    var scale by remember { mutableStateOf(0.5f) }
    var alpha by remember { mutableStateOf(0f) }
    
    val scaleAnimation by animateFloatAsState(
        targetValue = scale,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    
    val alphaAnimation by animateFloatAsState(
        targetValue = alpha,
        animationSpec = tween(1000),
        label = "alpha"
    )
    
    LaunchedEffect(Unit) {
        delay(300)
        scale = 1.2f
        alpha = 1f
        delay(400)
        scale = 1f
        delay(1800)
        
        val onboardingCompleted = sharedPrefs.getBoolean("onboarding_completed", false)
        val destination = if (onboardingCompleted) {
            Screen.MainMenu.route
        } else {
            Screen.Onboarding.route
        }
        
        navController.navigate(destination) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedBackground()
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .alpha(alphaAnimation)
                .scale(scaleAnimation)
        ) {
            Text(
                text = "😊⭐🔥",
                fontSize = 64.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Funny Combination",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

