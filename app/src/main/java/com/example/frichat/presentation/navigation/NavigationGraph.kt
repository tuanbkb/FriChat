package com.example.frichat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.frichat.presentation.screen.login.LoginScreen
import com.example.frichat.presentation.screen.signup.SignUpScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onLoginSuccess = {},
                onForgotPasswordClick = {},
                onSignUpClick = { navController.navigate(Screen.SignUpScreen.route) }
            )
        }

        composable(Screen.SignUpScreen.route) {
            SignUpScreen(
                onReturnClick = { navController.navigate(Screen.LoginScreen.route) },
            )
        }
    }
}