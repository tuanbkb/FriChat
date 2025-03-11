package com.example.frichat.presentation.screen.chatlist

import com.example.frichat.domain.model.Chat

data class ChatListState(
    val chats : List<Chat> = emptyList()
)
