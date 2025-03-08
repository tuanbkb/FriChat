package com.example.frichat.domain.repository

import com.example.frichat.data.model.AuthResult

interface AuthRepository{
    suspend fun registerUser(email: String, username: String, password: String): AuthResult
    suspend fun saveUserToFirestore(email: String, username: String, userId: String): AuthResult
}