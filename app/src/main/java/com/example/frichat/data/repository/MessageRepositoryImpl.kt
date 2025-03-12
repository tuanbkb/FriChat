package com.example.frichat.data.repository

import android.util.Log
import com.example.frichat.data.mapper.MessageMapper
import com.example.frichat.data.model.ChatDTO
import com.example.frichat.data.model.MessageDTO
import com.example.frichat.domain.model.Message
import com.example.frichat.domain.repository.MessageRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : MessageRepository {
    override suspend fun listenToMessage(
        chatId: String,
        onMessageUpdate: (List<Message>) -> Unit
    ): ListenerRegistration {
        return firestore.collection("chats").document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener

                val updatedMessage = snapshot?.documents?.mapNotNull { document ->
                    val temp = document.toObject(MessageDTO::class.java) ?: MessageDTO()
                    MessageMapper.mapToDomain(temp)
                } ?: emptyList()

                onMessageUpdate(updatedMessage)
            }
    }

    override suspend fun sendMessage(chatId: String, message: Message): Result<Unit> {
        return try {
            firestore.collection("chats").document(chatId)
                .collection("messages")
                .add(MessageMapper.mapToDTO(message))
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}