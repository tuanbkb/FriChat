package com.example.frichat.domain.usecase

import com.example.frichat.domain.repository.UserRepository
import javax.inject.Inject

class ChangeUsernameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(uid: String, username: String) : Result<Unit> {
        if (username.isBlank()) return Result.failure(Exception("Username can't be blank"))
        return userRepository.changeUsername(uid, username)
    }
}