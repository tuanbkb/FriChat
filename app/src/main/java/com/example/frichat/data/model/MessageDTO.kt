package com.example.frichat.data.model

import com.google.firebase.Timestamp

data class MessageDTO (
    val senderId: String = "",
    val text: String = "",
    val timestamp: Timestamp = Timestamp.now()
)