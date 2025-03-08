package com.example.frichat.presentation.screen.login

import com.example.frichat.data.model.AuthResult

data class LoginState(
    val email: String = "",
    val password: String = "",
    val loginState: AuthResult = AuthResult.Idle,
    val errorMessage: String = ""
)
