package com.example.frichat.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frichat.data.model.AuthResult
import com.example.frichat.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun onLoginClick() {
        viewModelScope.launch {
            _state.update { it.copy(loginState = AuthResult.Loading, errorMessage = "") }
            val result = loginUseCase.invoke(_state.value.email, _state.value.password)
            _state.update { it.copy(loginState = result) }
        }
    }

    fun setErrorMessage(errorMessage: String) {
        _state.update { it.copy(errorMessage = errorMessage) }
    }
}