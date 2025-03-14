package com.example.frichat.domain.usecase

import com.example.frichat.data.model.AuthResult
import com.example.frichat.domain.repository.AuthRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        oldPassword: String,
        newPassword: String,
        confirmNewPassword: String
    ): AuthResult {
        if (oldPassword.isBlank()) return AuthResult.Error("Old password can't be blank")
        if (newPassword.isBlank()) return AuthResult.Error("New password can't be blank")
        if (confirmNewPassword.isBlank()) return AuthResult.Error("Confirm password can't be blank")
        if (newPassword != confirmNewPassword) return AuthResult.Error("Password doesn't match")
        return authRepository.changePassword(oldPassword, newPassword)
    }
}