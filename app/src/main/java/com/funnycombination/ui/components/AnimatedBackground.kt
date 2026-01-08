package com.funnycombination.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnimatedBackground(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "background")
    
    val color1Progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color1"
    )
    
    val color2Progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color2"
    )
    
    val color3Progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color3"
    )
    
    val color1Base = Color(0xFF1a1a2e)
    val color1Target = Color(0xFF16213e)
    val color1 = lerp(color1Base, color1Target, color1Progress)
    
    val color2Base = Color(0xFF0f3460)
    val color2Target = Color(0xFF1a1a2e)
    val color2 = lerp(color2Base, color2Target, color2Progress)
    
    val color3Base = Color(0xFF16213e)
    val color3Target = Color(0xFF0f3460)
    val color3 = lerp(color3Base, color3Target, color3Progress)
    
    val offset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset1"
    )
    
    val offset2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset2"
    )
    
    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        
        val centerX = width / 2
        val centerY = height / 2
        
        val radius1 = width * 0.6f
        val radius2 = width * 0.8f
        val radius3 = width * 1.0f
        
        val x1 = centerX + cos(offset1 * 2 * Math.PI).toFloat() * width * 0.2f
        val y1 = centerY + sin(offset1 * 2 * Math.PI).toFloat() * height * 0.2f
        
        val x2 = centerX + cos(offset2 * 2 * Math.PI + Math.PI / 3).toFloat() * width * 0.15f
        val y2 = centerY + sin(offset2 * 2 * Math.PI + Math.PI / 3).toFloat() * height * 0.15f
        
        val x3 = centerX + cos((1 - offset1) * 2 * Math.PI + Math.PI / 6).toFloat() * width * 0.1f
        val y3 = centerY + sin((1 - offset1) * 2 * Math.PI + Math.PI / 6).toFloat() * height * 0.1f
        
        val color1WithAlpha1 = Color(
            red = color1.red,
            green = color1.green,
            blue = color1.blue,
            alpha = 0.4f
        )
        val color1WithAlpha2 = Color(
            red = color1.red,
            green = color1.green,
            blue = color1.blue,
            alpha = 0.0f
        )
        
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(color1WithAlpha1, color1WithAlpha2),
                center = Offset(x1, y1),
                radius = radius1
            ),
            radius = radius1,
            center = Offset(x1, y1)
        )
        
        val color2WithAlpha1 = Color(
            red = color2.red,
            green = color2.green,
            blue = color2.blue,
            alpha = 0.3f
        )
        val color2WithAlpha2 = Color(
            red = color2.red,
            green = color2.green,
            blue = color2.blue,
            alpha = 0.0f
        )
        
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(color2WithAlpha1, color2WithAlpha2),
                center = Offset(x2, y2),
                radius = radius2
            ),
            radius = radius2,
            center = Offset(x2, y2)
        )
        
        val color3WithAlpha1 = Color(
            red = color3.red,
            green = color3.green,
            blue = color3.blue,
            alpha = 0.25f
        )
        val color3WithAlpha2 = Color(
            red = color3.red,
            green = color3.green,
            blue = color3.blue,
            alpha = 0.0f
        )
        
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(color3WithAlpha1, color3WithAlpha2),
                center = Offset(x3, y3),
                radius = radius3
            ),
            radius = radius3,
            center = Offset(x3, y3)
        )
    }
}

