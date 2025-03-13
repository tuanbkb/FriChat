package com.example.frichat.presentation.screen.menu

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frichat.domain.usecase.ChangeUsernameUseCase
import com.example.frichat.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val changeUsernameUseCase: ChangeUsernameUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MenuState())
    val state: StateFlow<MenuState> = _state.asStateFlow()

    fun logout() {
        auth.signOut()
    }

    fun hideDialog() {
        _state.update { it.copy(showDialog = false) }
    }

    fun showDialog() {
        _state.update { it.copy(showDialog = true) }
    }

    fun onUsernameChange(username: String) {
        _state.update { it.copy(username = username) }
    }

    fun changeUsername(userViewModel: UserViewModel, context: Context) {
        viewModelScope.launch {
            _state.update { it.copy(showDialog = false, loading = true) }

            val result =
                changeUsernameUseCase.invoke(userViewModel.user.value.uid, _state.value.username)

            _state.update { it.copy(loading = false) }

            if (result.isSuccess) {
                Toast.makeText(
                    context,
                    "Username changed successfully!",
                    Toast.LENGTH_LONG
                ).show()
                userViewModel.setUsername(username = _state.value.username)
            } else Toast.makeText(
                context,
                "Error changing username. Please try again later",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}