package com.example.frichat.presentation.navigation

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login_screen")
    object SignUpScreen: Screen("signup_screen")
    object ResetPasswordScreen: Screen("reset_password_screen")
    object MainScreen: Screen("main_screen")
}