package com.example.frichat.data.repository

import android.net.Uri
import android.util.Log
import com.example.frichat.data.mapper.UserMapper
import com.example.frichat.data.model.UserDTO
import com.example.frichat.domain.model.User
import com.example.frichat.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : UserRepository {
    override suspend fun getUserFromUid(uid: String): Result<User> {
        return try {
            val document = firestore.collection("users").document(uid).get().await()
            if (!document.exists()) throw Exception("User not found")

            val userDTO = document.toObject(UserDTO::class.java)
                ?: throw Exception("Failed to parse user data")

            Result.success(UserMapper.mapToDomain(userDTO))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCurrentLoginUser(): Result<User> {
        return try {
            val userUid = firebaseAuth.currentUser?.uid
            val user = getUserFromUid(userUid!!).getOrThrow()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changeUsername(uid: String, username: String): Result<Unit> {
        return try {
            firestore.collection("users").document(uid).update(
                mapOf("username" to username)
            ).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}