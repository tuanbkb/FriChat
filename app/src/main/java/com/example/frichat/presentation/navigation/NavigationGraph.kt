package com.example.frichat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.frichat.presentation.screen.login.LoginScreen
import com.example.frichat.presentation.screen.main.MainScreen
import com.example.frichat.presentation.screen.resetpassword.ResetPasswordScreen
import com.example.frichat.presentation.screen.signup.SignUpScreen
import com.example.frichat.viewmodel.UserViewModel

@Composable
fun NavigationGraph(
    userViewModel: UserViewModel,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                userViewModel = userViewModel,
                onLoginSuccess = { navController.navigate(Screen.MainScreen.route) },
                onForgotPasswordClick = { navController.navigate(Screen.ResetPasswordScreen.route) },
                onSignUpClick = { navController.navigate(Screen.SignUpScreen.route) }
            )
        }

        composable(Screen.SignUpScreen.route) {
            SignUpScreen(
                onReturnClick = { navController.navigate(Screen.LoginScreen.route) },
            )
        }

        composable(Screen.ResetPasswordScreen.route) {
            ResetPasswordScreen(
                onReturnClick = { navController.navigate(Screen.LoginScreen.route) }
            )
        }

        composable(Screen.MainScreen.route) {
            MainScreen(
                userViewModel = userViewModel
            )
        }
    }
}