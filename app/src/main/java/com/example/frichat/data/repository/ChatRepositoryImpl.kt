package com.example.frichat.data.repository

import android.util.Log
import com.example.frichat.data.mapper.ChatMapper
import com.example.frichat.data.model.ChatDTO
import com.example.frichat.domain.model.Chat
import com.example.frichat.domain.repository.ChatRepository
import com.example.frichat.domain.repository.UserRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ChatRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userRepository: UserRepository
) : ChatRepository {
    override suspend fun listenChatList(
        uid: String,
        viewModelScope: CoroutineScope,
        onChatUpdate: (List<Chat>) -> Unit
    ): ListenerRegistration {
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

    override suspend fun getOrCreateChatByUsers(userId1: String, userId2: String): String {
        val querySnap = firestore.collection("chats")
            .whereArrayContains("users", userId1)
            .get().await()
        val result = querySnap.documents.firstOrNull { doc ->
            val users = doc.get("users") as? List<String> ?: emptyList()
            userId2 in users
        }

        if (result != null) {
            return result.id
        }

        val newChat = ChatDTO(
            id = "",
            lastMessage = "",
            updateAt = Timestamp.now(),
            users = listOf(userId1, userId2)
        )

        return suspendCoroutine { continuation ->
            firestore.collection("chats").add(newChat).addOnSuccessListener { doc ->
                Log.d("DEBUG", "New chat created: ${doc.id}")
                val chatId = doc.id
                firestore.collection("chats").document(chatId).update("id", chatId)
                continuation.resume(chatId)
            }
        }

    }
}