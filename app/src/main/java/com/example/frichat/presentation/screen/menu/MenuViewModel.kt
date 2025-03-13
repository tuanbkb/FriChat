package com.example.frichat.presentation.screen.menu

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {
    fun logout() {
        auth.signOut()
    }
}