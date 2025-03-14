package com.example.frichat.presentation.screen.menu

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frichat.data.model.AuthResult
import com.example.frichat.domain.usecase.ChangePasswordUseCase
import com.example.frichat.domain.usecase.ChangeUsernameUseCase
import com.example.frichat.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val changeUsernameUseCase: ChangeUsernameUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MenuState())
    val state: StateFlow<MenuState> = _state.asStateFlow()

    fun logout() {
        auth.signOut()
    }

    fun hideDialog() {
        _state.update { it.copy(showDialog = false) }
    }

    fun showDialog() {
        _state.update { it.copy(showDialog = true) }
    }

    fun hideChangePasswordDialog() {
        _state.update { it.copy(showChangePasswordDialog = false) }
    }

    fun showChangePasswordDialog() {
        _state.update { it.copy(showChangePasswordDialog = true) }
    }

    fun onUsernameChange(username: String) {
        _state.update { it.copy(username = username) }
    }

    fun onOldPasswordChange(oldPassword: String) {
        _state.update { it.copy(oldPassword = oldPassword) }
    }

    fun onNewPasswordChange(newPassword: String) {
        _state.update { it.copy(newPassword = newPassword) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _state.update { it.copy(confirmPassword = confirmPassword) }
    }

    fun changeUsername(userViewModel: UserViewModel, context: Context) {
        viewModelScope.launch {
            _state.update { it.copy(showDialog = false, loading = true) }

            val result =
                changeUsernameUseCase.invoke(userViewModel.user.value.uid, _state.value.username)

            _state.update { it.copy(loading = false) }

            if (result.isSuccess) {
                Toast.makeText(
                    context,
                    "Username changed successfully!",
                    Toast.LENGTH_LONG
                ).show()
                userViewModel.setUsername(username = _state.value.username)
            } else Toast.makeText(
                context,
                "Error changing username. Please try again later",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun changePassword(context: Context) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, errorMessage = "") }
            val result = changePasswordUseCase.invoke(
                oldPassword = _state.value.oldPassword,
                newPassword = _state.value.newPassword,
                confirmNewPassword = _state.value.confirmPassword
            )
//            _state.update { it.copy(loading = false) }
            when (result) {
                is AuthResult.Success -> {
                    _state.update { it.copy(loading = false, showChangePasswordDialog = false) }
                    Toast.makeText(
                        context,
                        "Password changed successfully",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is AuthResult.Error -> {
                    _state.update { it.copy(loading = false, errorMessage = result.message) }
                }
                else -> {}
            }
        }
    }
}