package com.example.frichat.presentation.screen.menu

data class MenuState(
    val showDialog: Boolean = false,
    val username: String = "",
    val oldPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val errorMessage: String = "",
    val loading: Boolean = false,
    val success: Boolean = true,
    val showChangePasswordDialog: Boolean = false,
    val changePasswordErrorMessage: String = ""
)
