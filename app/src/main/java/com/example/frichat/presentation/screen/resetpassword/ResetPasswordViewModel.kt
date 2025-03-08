package com.example.frichat.presentation.screen.resetpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frichat.data.model.AuthResult
import com.example.frichat.domain.usecase.ResetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(private val resetPasswordUseCase: ResetPasswordUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(ResetPasswordState())
    val state: StateFlow<ResetPasswordState> = _state.asStateFlow()

    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun showDialog() {
        _state.update { it.copy(showDialog = true) }
    }

    fun hideDialog() {
        _state.update { it.copy(showDialog = false) }
    }

    fun sendEmail() {
        viewModelScope.launch {
            _state.update { it.copy(resetPasswordState = AuthResult.Loading, errorMessage = "") }
            val result = resetPasswordUseCase.invoke(email = _state.value.email)
            _state.update { it.copy(resetPasswordState = result) }
        }
    }

    fun setErrorMessage(errorMessage: String) {
        _state.update { it.copy(errorMessage = errorMessage) }
    }
}