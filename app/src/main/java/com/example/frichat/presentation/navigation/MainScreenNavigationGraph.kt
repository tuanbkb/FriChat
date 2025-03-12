package com.example.frichat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.frichat.presentation.screen.chatlist.ChatListScreen
import com.example.frichat.presentation.screen.menu.MenuScreen
import com.example.frichat.viewmodel.UserViewModel

@Composable
fun MainScreenNavigationGraph(
    navController: NavHostController,
    userViewModel: UserViewModel,
    onChatClick: (String, String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = "Chat"
    ) {
        composable("Chat") {
            ChatListScreen(userViewModel = userViewModel, onChatClick = onChatClick)
        }

        composable("Menu") {
            MenuScreen(userViewModel = userViewModel)
        }
    }
}