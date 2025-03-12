package com.example.frichat.data.repository

import android.util.Log
import com.example.frichat.data.mapper.ChatMapper
import com.example.frichat.data.model.ChatDTO
import com.example.frichat.domain.model.Chat
import com.example.frichat.domain.repository.ChatRepository
import com.example.frichat.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userRepository: UserRepository
) : ChatRepository {
    override suspend fun listenChatList(
        uid: String,
        viewModelScope: CoroutineScope,
        onChatUpdate: (List<Chat>) -> Unit
    ): ListenerRegistration {
        val ref = firestore.collection("chats").whereArrayContains("users", uid)
        return firestore.collection("chats").whereArrayContains("users", uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener

                viewModelScope.launch {
                    val updatedChat = snapshot?.documents?.mapNotNull { document ->
                        val temp = document.toObject(ChatDTO::class.java) ?: ChatDTO()
                        ChatMapper.mapToDomain(temp, uid, userRepository)
                    } ?: emptyList()
                    onChatUpdate(updatedChat)
                }
            }
    }
}