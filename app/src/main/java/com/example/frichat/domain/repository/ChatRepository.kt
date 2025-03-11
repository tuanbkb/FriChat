package com.example.frichat.domain.repository

import com.example.frichat.domain.model.Chat
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope

interface ChatRepository {
    suspend fun listenChatList(
        uid: String,
        viewModelScope: CoroutineScope,
        onChatUpdate: (List<Chat>) -> Unit
    ): ListenerRegistration
}