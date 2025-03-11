package com.example.frichat.data.mapper

import com.example.frichat.data.model.ChatDTO
import com.example.frichat.domain.model.Chat
import com.example.frichat.domain.model.User
import com.example.frichat.domain.repository.UserRepository

object ChatMapper {
    suspend fun mapToDomain(
        chatDTO: ChatDTO,
        userUid: String,
        userRepository: UserRepository
    ): Chat {
        val targetUid = chatDTO.users.first { it != userUid }
        val targetUser = userRepository.getUserFromUid(targetUid).getOrNull()
            ?: User("", "", "", "")
        return Chat(
            id = chatDTO.id,
            lastMessage = chatDTO.lastMessage,
            lastUpdate = chatDTO.updateAt.toDate(),
            targetUser = targetUser
        )
    }
}