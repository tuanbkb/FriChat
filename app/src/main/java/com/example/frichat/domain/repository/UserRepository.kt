package com.example.frichat.domain.repository

import com.example.frichat.data.model.UserDTO
import com.example.frichat.domain.model.User

interface UserRepository {
    suspend fun getUserFromUid(uid: String): Result<User>
    suspend fun getCurrentLoginUser(): Result<User>
}