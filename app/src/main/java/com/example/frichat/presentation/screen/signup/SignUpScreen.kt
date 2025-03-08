package com.example.frichat.presentation.screen.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frichat.data.model.AuthResult
import com.example.frichat.presentation.component.AuthTextField
import com.example.frichat.presentation.component.CircularProgressIndicatorFullSize
import com.example.frichat.ui.theme.AppTheme

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onReturnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = state.registerState) {
        state.registerState.let {
            when (it) {
                is AuthResult.Success -> viewModel.showDialog()
                is AuthResult.Error -> viewModel.setErrorMessage(it.message)
                else -> {}
            }
        }
    }

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
            if (state.errorMessage.isNotBlank()) {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
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

    if (state.registerState is AuthResult.Loading) {
        CircularProgressIndicatorFullSize()
    }

    if (state.showDialog) {
        SignUpSuccessDialog(viewModel = viewModel, onRegisterSuccess = onReturnClick)
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

@Composable
fun SignUpSuccessDialog(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel,
    onRegisterSuccess: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { viewModel.hideDialog() },
        confirmButton = {
            TextButton(
                onClick = { onRegisterSuccess() }
            ) { Text(text = "To Login Screen") }
        },
        dismissButton = {
            TextButton(
                onClick = { viewModel.hideDialog() }
            ) { Text(text = "OK") }
        },
        title = { Text(text = "SUCCESS") },
        text = {
            Text(text = "Account create successfully. Please login!")
        }
    )
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