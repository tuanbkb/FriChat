package com.example.frichat.presentation.screen.resetpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import com.example.frichat.data.model.AuthResult
import com.example.frichat.presentation.component.AuthTextField
import com.example.frichat.presentation.component.CircularProgressIndicatorFullSize
import com.example.frichat.ui.theme.AppTheme

@Composable
fun ResetPasswordScreen(
    viewModel: ResetPasswordViewModel = hiltViewModel(),
    onReturnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = state.resetPasswordState) {
        state.resetPasswordState.let {
            when (it) {
                is AuthResult.Success -> viewModel.showDialog()
                is AuthResult.Error -> viewModel.setErrorMessage(it.message)
                else -> {}
            }
        }
    }
    Scaffold(
        topBar = { ResetPasswordTopBar(onReturnClick = onReturnClick) },
        modifier = modifier
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(text = "Enter your email:", modifier = Modifier.padding(bottom = 8.dp))
            AuthTextField(
                label = "Email",
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it) },
                imeAction = ImeAction.Done
            )
            if (state.errorMessage.isNotBlank()) {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            Button(
                onClick = { viewModel.sendEmail() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(text = "Submit", modifier = Modifier.padding(4.dp))
            }
        }
    }

    if (state.resetPasswordState is AuthResult.Loading) {
        CircularProgressIndicatorFullSize()
    }

    if (state.showDialog) {
        ResetPasswordDialog(viewModel = viewModel, onReturnClick = onReturnClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordTopBar(
    modifier: Modifier = Modifier,
    onReturnClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "Reset Password") },
        navigationIcon = {
            IconButton(
                onClick = onReturnClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go Back"
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun ResetPasswordDialog(
    viewModel: ResetPasswordViewModel,
    onReturnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { viewModel.hideDialog() },
        confirmButton = {
            TextButton(
                onClick = onReturnClick
            ) { Text(text = "Confirm") }
        },
        text = {
            Text(
                text = "We just sent you an email to reset your password. " +
                        "Please check your inbox to proceed"
            )
        },
        title = { Text(text = "Reset password email sent") },
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResetPasswordScreenPreview() {
    AppTheme {
        ResetPasswordScreen(onReturnClick = {})
    }
}