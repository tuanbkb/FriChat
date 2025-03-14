package com.example.frichat.presentation.screen.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.frichat.R
import com.example.frichat.presentation.component.AuthTextField
import com.example.frichat.presentation.component.CircularProgressIndicatorFullSize
import com.example.frichat.viewmodel.UserViewModel

@Composable
fun MenuScreen(
    menuViewModel: MenuViewModel = hiltViewModel(),
    userViewModel: UserViewModel,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by menuViewModel.state.collectAsState()
    val user by userViewModel.user.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text(
                text = user.username[0].toString(),
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = user.username,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            MenuItem(
                icon = R.drawable.edit,
                text = "Change username",
                onClick = { menuViewModel.showDialog() }
            )
            MenuItem(
                icon = R.drawable.security,
                text = "Change password",
                onClick = { menuViewModel.showChangePasswordDialog() }
            )
            MenuItem(
                icon = R.drawable.logout,
                text = "Logout",
                onClick = {
                    menuViewModel.logout()
                    onLogout()
                }
            )
        }
    }

    if (state.showDialog) {
        ChangeUsernameDialog(
            viewModel = menuViewModel,
            userViewModel = userViewModel
        )
    }

    if (state.showChangePasswordDialog) {
        ChangePasswordDialog(
            viewModel = menuViewModel
        )
    }
}

@Composable
fun MenuItem(
    icon: Int,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun ChangeUsernameDialog(
    viewModel: MenuViewModel,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.onUsernameChange(userViewModel.user.value.username)
    }

    AlertDialog(
        onDismissRequest = { viewModel.hideDialog() },
        confirmButton = {
            TextButton(
                onClick = { viewModel.changeUsername(userViewModel, context) }
            ) {
                Text(text = "Confirm")
            }
        },
        title = { Text(text = "Change Username") },
        text = {
            Column {
                Text(text = "Enter your new username:")
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = state.username,
                    onValueChange = { viewModel.onUsernameChange(it) }
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun ChangePasswordDialog(
    viewModel: MenuViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = { viewModel.hideChangePasswordDialog() },
        title = { Text(text = "Change password") },
        text = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AuthTextField(
                        label = "Old Password",
                        value = state.oldPassword,
                        onValueChange = { viewModel.onOldPasswordChange(it) },
                        isPassword = true
                    )
                    AuthTextField(
                        label = "New Password",
                        value = state.newPassword,
                        onValueChange = { viewModel.onNewPasswordChange(it) },
                        isPassword = true
                    )
                    AuthTextField(
                        label = "Confirm Password",
                        value = state.confirmPassword,
                        onValueChange = { viewModel.onConfirmPasswordChange(it) },
                        isPassword = true,
                        imeAction = ImeAction.Done
                    )
                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
                if (state.loading) {
                    CircularProgressIndicatorFullSize(modifier.matchParentSize())
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { viewModel.changePassword(context = context) }
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { viewModel.hideChangePasswordDialog() }
            ) {
                Text(text = "Cancel")
            }
        },
        modifier = modifier
    )
}