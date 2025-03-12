package com.example.frichat.domain.repository

import com.example.frichat.domain.model.Message
import com.google.firebase.firestore.ListenerRegistration

interface MessageRepository {
    suspend fun listenToMessage(
        chatId: String,
        onMessageUpdate: (List<Message>) -> Unit
    ): ListenerRegistration

    suspend fun sendMessage(chatId: String, message: Message): Result<Unit>
}