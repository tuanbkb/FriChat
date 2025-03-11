package com.example.frichat.domain.usecase

import android.util.Log
import com.example.frichat.domain.model.Chat
import com.example.frichat.domain.repository.ChatRepository
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ListenToChatListUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        uid: String,
        viewModelScope: CoroutineScope,
        onChatUpdate: (List<Chat>) -> Unit
    ) : ListenerRegistration {
        Log.d("DEBUG", "Launched from use case")
        return chatRepository.listenChatList(uid, viewModelScope, onChatUpdate)
    }
}