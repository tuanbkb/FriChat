package com.example.frichat.data.model

sealed class AuthResult {
    data object Success: AuthResult()
    data object Loading: AuthResult()
    data object Idle: AuthResult()
    data class Error(val message: String): AuthResult()
}
