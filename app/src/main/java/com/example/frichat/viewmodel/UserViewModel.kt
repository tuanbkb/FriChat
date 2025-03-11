package com.example.frichat.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.frichat.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserViewModel: ViewModel() {
    private val _user = MutableStateFlow(User("", "", "", ""))
    val user : StateFlow<User> = _user.asStateFlow()

    fun setUser(user: User) {
        _user.value = user
    }

    fun setProfilePicture(profilePicture: String) {
        _user.update { it.copy(profilePicture = profilePicture) }
    }
}
