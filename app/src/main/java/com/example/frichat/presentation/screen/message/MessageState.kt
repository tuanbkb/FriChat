package com.example.frichat.presentation.screen.message

import com.example.frichat.domain.model.Message
import com.example.frichat.domain.model.User
import java.util.Date

data class MessageState(
    val messageList: List<Message> = emptyList(),
    val message: String = "",
    val targetUser: User = User("", "Username", "", ""),
    val showTimeMessage: Date? = null
)
