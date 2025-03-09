package com.example.frichat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.frichat.presentation.screen.chatlist.ChatListScreen
import com.example.frichat.presentation.screen.menu.MenuScreen

@Composable
fun MainScreenNavigationGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "Chat"
    ) {
        composable("Chat") {
            ChatListScreen()
        }

        composable("Menu") {
            MenuScreen()
        }
    }
}