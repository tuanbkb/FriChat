package com.example.frichat.presentation.screen.signup

import com.example.frichat.data.model.AuthResult

data class SignUpState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val registerState: AuthResult = AuthResult.Idle,
    val errorMessage: String = "",
    val showDialog: Boolean = false
)
