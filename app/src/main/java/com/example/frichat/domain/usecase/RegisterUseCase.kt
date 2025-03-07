package com.example.frichat.domain.usecase

import com.example.frichat.data.model.AuthResult
import com.example.frichat.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, username: String, password: String): AuthResult {
        if (email.isBlank()) return AuthResult.Error("Email can't be blank")
        if (password.isBlank()) return AuthResult.Error("Password can't be blank")
        if (username.isBlank()) return AuthResult.Error("Username can't be blank")
        return authRepository.registerUser(email, username, password)
    }
}