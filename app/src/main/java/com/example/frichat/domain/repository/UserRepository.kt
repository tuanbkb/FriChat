package com.example.frichat.domain.repository

import android.net.Uri
import com.example.frichat.data.model.UserDTO
import com.example.frichat.domain.model.User
import java.net.URI

interface UserRepository {
    suspend fun getUserFromUid(uid: String): Result<User>
    suspend fun getCurrentLoginUser(): Result<User>
}