package com.example.frichat.domain.model

import java.util.Date

data class Message(
    val senderId: String,
    val text: String,
    val timestamp: Date
)
