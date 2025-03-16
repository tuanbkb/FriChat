package com.example.frichat.presentation.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.frichat.R
import com.example.frichat.presentation.navigation.MainScreenNavigationGraph
import com.example.frichat.viewmodel.UserViewModel

@Composable
fun MainScreen(
    userViewModel: UserViewModel,
    onChatClick: (String, String) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController: NavHostController = rememberNavController()
    Scaffold(
        bottomBar = { MainBottomBar(navController = navController) },
        modifier = modifier
    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)) {
            MainScreenNavigationGraph(
                navController = navController,
                userViewModel = userViewModel,
                onChatClick = onChatClick,
                onLogout = onLogout
            )
        }
    }
}

@Composable
fun MainBottomBar(navController: NavController, modifier: Modifier = Modifier) {
    val screens = listOf("Chat", "Search", "Menu")
    val currentScreen = remember { mutableStateOf("Chat") }
    val icons = listOf(R.drawable.chat_bubble, R.drawable.search, R.drawable.menu)
    NavigationBar(
        modifier = modifier
    ) {
        screens.forEachIndexed { index, screen ->
            NavigationBarItem(
                selected = currentScreen.value == screen,
                onClick = {
                    navController.navigate(screen)
                    currentScreen.value = screen
                },
                icon = {
                    Icon(
                        painter = painterResource(icons[index]),
                        contentDescription = screen
                    )
                },
                label = { Text(text = screen) },
                alwaysShowLabel = false,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
        }
    }
}