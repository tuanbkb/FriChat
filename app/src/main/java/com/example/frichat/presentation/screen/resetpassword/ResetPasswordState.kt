package com.example.frichat.presentation.screen.resetpassword

import com.example.frichat.data.model.AuthResult

data class ResetPasswordState(
    val email: String = "",
    val showDialog: Boolean = false,
    val resetPasswordState: AuthResult = AuthResult.Idle,
    val errorMessage: String = ""
)
