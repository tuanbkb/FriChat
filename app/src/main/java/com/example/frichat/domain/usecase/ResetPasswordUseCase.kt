package com.example.frichat.domain.usecase

import com.example.frichat.data.model.AuthResult
import com.example.frichat.domain.repository.AuthRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String): AuthResult {
        if (email.isBlank()) return AuthResult.Error("Email can't be blank")
        return authRepository.resetPassword(email)
    }
}