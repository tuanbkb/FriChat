package com.example.frichat.presentation.screen.menu

data class MenuState(
    val showDialog: Boolean = false,
    val username: String = "",
    val loading: Boolean = false,
    val success: Boolean = true
)
