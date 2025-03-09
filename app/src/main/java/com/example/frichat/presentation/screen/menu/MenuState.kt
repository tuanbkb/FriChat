package com.example.frichat.presentation.screen.menu

import com.example.frichat.domain.model.User

data class MenuState(
    val user: User = User("", "", "", "")
)
