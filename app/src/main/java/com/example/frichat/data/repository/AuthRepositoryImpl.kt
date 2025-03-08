package com.example.frichat.data.repository

import com.example.frichat.data.model.AuthResult
import com.example.frichat.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {
    override suspend fun registerUser(email: String, username: String, password: String): AuthResult {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: return AuthResult.Error("Error retrieving UID")
            saveUserToFirestore(email, username, userId)
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error(e.localizedMessage ?: "Unknown Error!")
        }
    }

    override suspend fun saveUserToFirestore(email: String, username: String, userId: String): AuthResult {
        return try {
            val user = mapOf(
                email to "email",
                username to "username"
            )
            firestore.collection("users").document(userId).set(user).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error("Error saving user information: ${e.localizedMessage}")
        }
    }

    override suspend fun loginUser(email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error("Login credentials do not match any user" ?: "Unknown Error")
        }
    }
}