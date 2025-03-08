package com.example.frichat.presentation.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.frichat.R
import com.example.frichat.data.model.AuthResult
import com.example.frichat.presentation.component.AuthTextField
import com.example.frichat.presentation.component.CircularProgressIndicatorFullSize
import com.example.frichat.ui.theme.AppTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = state.loginState) {
        state.loginState.let {
            when (it) {
                is AuthResult.Success -> onLoginSuccess()
                is AuthResult.Error -> viewModel.setErrorMessage(it.message)
                else -> {}
            }
        }
    }

    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LoginTitle()
                Spacer(modifier = Modifier.height(16.dp))
                AuthTextField(
                    label = "Email",
                    value = state.email,
                    onValueChange = { viewModel.onEmailChange(it) },
                )
                AuthTextField(
                    label = "Password",
                    value = state.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    isPassword = true,
                    imeAction = ImeAction.Done
                )
                Spacer(modifier = Modifier.height(16.dp))
                LoginButtonRow(
                    onLoginClick = {
                        viewModel.onLoginClick()
                    },
                    onForgotPasswordClick = onForgotPasswordClick
                )
                if (state.errorMessage.isNotBlank()) {
                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Don't have an account?")
                OutlinedButton(
                    onClick = onSignUpClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Sign Up", modifier = Modifier.padding(4.dp))
                }
            }
        }
    }

    if (state.loginState is AuthResult.Loading) {
        CircularProgressIndicatorFullSize()
    }
}

@Composable
fun LoginTitle(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val imageHeight = ((MaterialTheme.typography.displayMedium.fontSize.value) * 1.5).dp
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.heightIn(max = imageHeight)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LoginButtonRow(
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onLoginClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Login", modifier = Modifier.padding(4.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedButton(
            onClick = onForgotPasswordClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Forgot Password", modifier = Modifier.padding(4.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    AppTheme {
        LoginScreen(
            onForgotPasswordClick = {},
            onSignUpClick = {},
            onLoginSuccess = {}
        )
    }
}