package com.example.frichat.domain.usecase

import com.example.frichat.domain.model.Message
import com.example.frichat.domain.repository.MessageRepository
import com.google.firebase.firestore.ListenerRegistration
import javax.inject.Inject

class ListenToMessageUseCase @Inject constructor(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(
        chatId: String,
        onMessageUpdate: (List<Message>) -> Unit
    ) : ListenerRegistration {
        return messageRepository.listenToMessage(chatId, onMessageUpdate)
    }
}