package com.example.frichat.presentation.screen.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frichat.domain.model.User
import com.example.frichat.domain.usecase.GetCurrentLoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getCurrentLoginUserUseCase: GetCurrentLoginUserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MenuState())
    val state: StateFlow<MenuState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val currentUser = getCurrentLoginUserUseCase.invoke().getOrNull()
                ?: User("", "", "")
            _state.update { it.copy(user = currentUser) }
        }
    }


}