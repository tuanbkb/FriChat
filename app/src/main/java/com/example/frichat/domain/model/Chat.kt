package com.example.frichat.domain.model

import java.util.Date

data class Chat(
    val id: String,
    val lastMessage: String,
    val lastUpdate: Date,
    val targetUser: User
)
