package com.example.frichat.presentation.screen.search

import com.example.frichat.domain.model.User

data class SearchState(
    val searchQuery: String = "",
    val searchResult: List<User> = emptyList(),
    val loading: Boolean = false
)
