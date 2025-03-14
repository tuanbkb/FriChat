package com.example.frichat.presentation.screen.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frichat.domain.model.User
import com.example.frichat.domain.usecase.GetUserUseCase
import com.example.frichat.domain.usecase.ListenToMessageUseCase
import com.example.frichat.domain.usecase.SendMessageUseCase
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val listenToMessageUseCase: ListenToMessageUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MessageState())
    val state: StateFlow<MessageState> = _state.asStateFlow()

    private val messageListener: ListenerRegistration? = null

    fun onMessageChange(message: String) {
        _state.update { it.copy(message = message) }
    }

    fun onSendButtonClick(userId: String, chatId: String) {
        viewModelScope.launch {
            sendMessageUseCase.invoke(
                message = _state.value.message,
                userId = userId,
                chatId = chatId
            )
        }
        _state.update { it.copy(message = "") }
    }

    fun getTargetUser(targetId: String) {
        viewModelScope.launch {
            val targetUser = getUserUseCase.invoke(targetId).getOrNull()
                ?: User("", "Username", "", "")
            _state.update { it.copy(targetUser = targetUser) }
        }

    }

    fun addListener(
        chatId: String,
    ) {
        viewModelScope.launch {
            listenToMessageUseCase.invoke(chatId = chatId) { messageList ->
                _state.update { it.copy(messageList = messageList) }
            }
        }
    }

    fun onMessageClick(messageTimestamp: Date) {
        if (_state.value.showTimeMessage != messageTimestamp) {
            _state.update { it.copy(showTimeMessage = messageTimestamp)}
        } else {
            _state.update { it.copy(showTimeMessage = null) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        messageListener?.remove()
    }
}