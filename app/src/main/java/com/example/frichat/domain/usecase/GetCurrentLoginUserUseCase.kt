package com.example.frichat.domain.usecase

import com.example.frichat.domain.model.User
import com.example.frichat.domain.repository.UserRepository
import javax.inject.Inject

class GetCurrentLoginUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Result<User> {
        return userRepository.getCurrentLoginUser()
    }
}