package com.example.frichat.domain.usecase

import com.example.frichat.domain.model.Message
import com.example.frichat.domain.repository.MessageRepository
import java.util.Date
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(message: String, userId: String, chatId: String): Result<Unit> {
        if (message.isBlank()) return Result.failure(Exception("Message can't be blank"))
        val mess = Message(
            senderId = userId,
            text = message,
            timestamp = Date()
        )

        return messageRepository.sendMessage(
            chatId = chatId,
            message = mess
        )
    }
}