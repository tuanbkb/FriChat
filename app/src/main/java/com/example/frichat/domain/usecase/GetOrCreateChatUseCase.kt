package com.example.frichat.domain.usecase

import com.example.frichat.domain.repository.ChatRepository
import javax.inject.Inject

class GetOrCreateChatUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(userId1: String, userId2: String): String {
        return chatRepository.getOrCreateChatByUsers(userId1, userId2)
    }
}