package com.example.frichat.domain.repository

import com.example.frichat.data.model.AuthResult
import com.example.frichat.data.model.User

interface AuthRepository{
    suspend fun registerUser(email: String, username: String, password: String): AuthResult
    suspend fun saveUserToFirestore(user: User, userId: String): AuthResult
}