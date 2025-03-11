package com.example.frichat.data.model

import com.google.firebase.Timestamp

data class ChatDTO(
    val id: String = "",
    val lastMessage: String = "",
    val updateAt: Timestamp = Timestamp.now(),
    val users: List<String> = emptyList()
)
