package com.example.frichat.presentation.screen.chatlist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frichat.domain.model.Chat
import com.example.frichat.domain.usecase.ListenToChatListUseCase
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val listenToChatListUseCase: ListenToChatListUseCase,
): ViewModel() {
    private val _state = MutableStateFlow(ChatListState())
    val state: StateFlow<ChatListState> = _state.asStateFlow()

    private var chatListener: ListenerRegistration? = null

    fun listenToChat(uid: String) {
        viewModelScope.launch {
            chatListener = listenToChatListUseCase.invoke(uid, viewModelScope) { chatList ->
                _state.update { it.copy(chats = chatList) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        chatListener?.remove()
    }
}