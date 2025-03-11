package com.example.frichat.domain.repository

import com.example.frichat.data.model.AuthResult
import com.example.frichat.viewmodel.UserViewModel

interface AuthRepository{
    suspend fun registerUser(email: String, username: String, password: String): AuthResult
    suspend fun saveUserToFirestore(email: String, username: String, userId: String): AuthResult
    suspend fun loginUser(email: String, password: String, userViewModel: UserViewModel): AuthResult
    suspend fun resetPassword(email: String): AuthResult
}