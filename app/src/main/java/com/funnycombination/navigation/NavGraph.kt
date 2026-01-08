package com.funnycombination.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.funnycombination.ui.screens.GameOverScreen
import com.funnycombination.ui.screens.GameScreen
import com.funnycombination.ui.screens.HighScoreScreen
import com.funnycombination.ui.screens.MainMenuScreen
import com.funnycombination.ui.screens.OnboardingScreen
import com.funnycombination.ui.screens.PrivacyPolicyScreen
import com.funnycombination.ui.screens.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object MainMenu : Screen("main_menu")
    object Game : Screen("game")
    object GameOver : Screen("game_over")
    object HighScore : Screen("high_score")
    object PrivacyPolicy : Screen("privacy_policy")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(navController = navController)
        }
        composable(Screen.MainMenu.route) {
            MainMenuScreen(navController = navController)
        }
        composable(Screen.Game.route) {
            GameScreen(navController = navController)
        }
        composable(Screen.GameOver.route) {
            GameOverScreen(navController = navController)
        }
        composable(Screen.HighScore.route) {
            HighScoreScreen(navController = navController)
        }
        composable(Screen.PrivacyPolicy.route) {
            PrivacyPolicyScreen(navController = navController)
        }
    }
}

