package com.example.frichat.domain.usecase

import com.example.frichat.data.model.AuthResult
import com.example.frichat.domain.repository.AuthRepository
import com.example.frichat.viewmodel.UserViewModel
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String, userViewModel: UserViewModel): AuthResult {
        if (email.isBlank()) return AuthResult.Error("Email can't be blank")
        if (password.isBlank()) return AuthResult.Error("Password can't be blank")
        return authRepository.loginUser(email, password, userViewModel)
    }
}