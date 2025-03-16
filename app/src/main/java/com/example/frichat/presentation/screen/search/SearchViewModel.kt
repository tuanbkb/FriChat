package com.example.frichat.presentation.screen.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frichat.domain.usecase.GetOrCreateChatUseCase
import com.example.frichat.domain.usecase.SearchUserUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val searchUserUseCase: SearchUserUseCase,
    private val getOrCreateChatUseCase: GetOrCreateChatUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    private val userId = firebaseAuth.currentUser?.uid ?: ""

    fun onSearchQueryChange(searchQuery: String) {
        _state.update { it.copy(searchQuery = searchQuery) }
    }

    init {
        viewModelScope.launch {
            _state.debounce(500)
                .filter { it.searchQuery.isNotEmpty() }
                .collectLatest { query ->
                    val result = searchUserUseCase(query.searchQuery).getOrNull() ?: emptyList()
                    val filteredResult = result.filter { it.uid != userId }
                    _state.update { it.copy(searchResult = filteredResult) }
                }
        }
    }

    fun onUserClick(targetId: String, onGetChatIdComplete: (String, String) -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val chatId = getOrCreateChatUseCase(userId, targetId)
            Log.d("DEBUG", "New chat ID: $chatId")
            onGetChatIdComplete(chatId, targetId)
        }
    }
}