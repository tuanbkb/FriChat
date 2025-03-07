package com.example.frichat.presentation.screen.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frichat.presentation.component.AuthTextField
import com.example.frichat.ui.theme.AppTheme

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onReturnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    // TODO: Handle register result

    Scaffold(
        topBar = { SignUpAppBar(onReturnClick = onReturnClick) },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SignUpInputField(
                viewModel = viewModel
            )
            Button(
                onClick = {
                    viewModel.registerUser()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Sign Up", modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpAppBar(
    onReturnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = "Sign Up") },
        navigationIcon = {
            IconButton(
                onClick = onReturnClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Return"
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun SignUpInputField(
    viewModel: SignUpViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        AuthTextField(
            label = "Email",
            value = state.email,
            onValueChange = { viewModel.onEmailChange(it) },
        )
        AuthTextField(
            label = "Username",
            value = state.username,
            onValueChange = { viewModel.onUsernameChange(it) },
        )
        AuthTextField(
            label = "Password",
            value = state.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            isPassword = true,
        )
        AuthTextField(
            label = "Confirm Password",
            value = state.confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChange(it) },
            isPassword = true,
            imeAction = ImeAction.Done
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    AppTheme {
        SignUpScreen(
            onReturnClick = {}
        )
    }
}