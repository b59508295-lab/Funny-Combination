package com.funnycombination.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.funnycombination.ui.components.AnimatedBackground

@Composable
fun PrivacyPolicyScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        AnimatedBackground()
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "Privacy Policy",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Privacy Policy",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = "Last updated: ${java.time.LocalDate.now()}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "1. General Information",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = "This privacy policy describes how we collect, use, and protect your information when using the Funny Combination app.",
                        fontSize = 16.sp
                    )
                    
                    Text(
                        text = "2. Information Collection",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = "The app stores only game results (high scores) locally on your device for display in the leaderboard. No personal information is collected or shared with third parties.",
                        fontSize = 16.sp
                    )
                    
                    Text(
                        text = "3. Information Usage",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = "Saved game results are used exclusively for app functionality and leaderboard display.",
                        fontSize = 16.sp
                    )
                    
                    Text(
                        text = "4. Data Protection",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = "All data is stored locally on your device. We do not have access to your data and do not share it with third parties.",
                        fontSize = 16.sp
                    )
                    
                    Text(
                        text = "5. Policy Changes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = "We reserve the right to update this privacy policy. Any changes will be published on this page.",
                        fontSize = 16.sp
                    )
                    
                    Text(
                        text = "6. Contact",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Text(
                        text = "If you have any questions about this privacy policy, please contact us.",
                        fontSize = 16.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(14.dp),
                        spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    ),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                onClick = { navController.popBackStack() }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Back",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

