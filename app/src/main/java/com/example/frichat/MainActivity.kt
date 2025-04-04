package com.example.frichat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.frichat.presentation.navigation.NavigationGraph
import com.example.frichat.presentation.screen.login.LoginScreen
import com.example.frichat.presentation.screen.signup.SignUpScreen
import com.example.frichat.ui.theme.AppTheme
import com.example.frichat.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController : NavHostController = rememberNavController()
            val userViewModel : UserViewModel = viewModel()
            AppTheme {
                NavigationGraph(
                    userViewModel = userViewModel,
                    navController = navController
                )
            }
        }
    }
}