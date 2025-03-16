package com.example.frichat.domain.usecase

import com.example.frichat.domain.model.User
import com.example.frichat.domain.repository.UserRepository
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String): Result<List<User>> {
        return userRepository.searchUserByUsername(username)
    }
}