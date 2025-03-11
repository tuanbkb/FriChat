package com.example.frichat.presentation.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.frichat.R
import com.example.frichat.presentation.navigation.MainScreenNavigationGraph
import com.example.frichat.viewmodel.UserViewModel

@Composable
fun MainScreen(
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {
    val navController: NavHostController = rememberNavController()
    Scaffold(
//        topBar = { MainTopBar() },
        bottomBar = { MainBottomBar(navController = navController) },
        modifier = modifier
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            MainScreenNavigationGraph(
                navController = navController,
                userViewModel = userViewModel
            )
        }
    }
}

@Composable
fun MainBottomBar(navController: NavController, modifier: Modifier = Modifier) {
    val screens = listOf("Chat", "Menu")
    val currentScreen = remember { mutableStateOf("Chat") }
    val icons = listOf(R.drawable.chat_bubble, R.drawable.menu)
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

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainTopBar(
//    modifier: Modifier = Modifier
//) {
//    TopAppBar(
//        title = {
//            Text(
//                text = stringResource(R.string.app_name),
//                style = MaterialTheme.typography.displaySmall,
//                fontWeight = FontWeight.Bold,
//                color = MaterialTheme.colorScheme.primary
//            )
//        },
//        modifier = modifier
//    )
//}