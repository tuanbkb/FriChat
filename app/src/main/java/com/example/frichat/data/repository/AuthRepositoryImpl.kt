package com.example.frichat.data.repository

import com.example.frichat.data.model.AuthResult
import com.example.frichat.domain.model.User
import com.example.frichat.domain.repository.AuthRepository
import com.example.frichat.domain.repository.UserRepository
import com.example.frichat.viewmodel.UserViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.Exception

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val userRepository: UserRepository
) : AuthRepository {
    override suspend fun registerUser(
        email: String,
        username: String,
        password: String
    ): AuthResult {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: return AuthResult.Error("Error retrieving UID")
            saveUserToFirestore(email, username, userId)
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error(e.localizedMessage ?: "Unknown Error!")
        }
    }

    override suspend fun saveUserToFirestore(
        email: String,
        username: String,
        userId: String
    ): AuthResult {
        return try {
            val user = mapOf(
                "uid" to userId,
                "email" to email,
                "username" to username,
                "pp" to ""
            )
            firestore.collection("users").document(userId).set(user).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error("Error saving user information: ${e.localizedMessage}")
        }
    }

    override suspend fun loginUser(
        email: String,
        password: String,
        userViewModel: UserViewModel
    ): AuthResult {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = userRepository.getCurrentLoginUser().getOrNull()
                ?: User("", "", "", "")
            userViewModel.setUser(user)
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error("Login credentials do not match any user")
        }
    }

    override suspend fun resetPassword(email: String): AuthResult {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error("Email not linked to any user")
        }
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String): AuthResult {
        val user = firebaseAuth.currentUser ?: return AuthResult.Error("User not sign in")
        val email = user.email ?: return AuthResult.Error("Email not found!")
        val credential = EmailAuthProvider.getCredential(email, oldPassword)
        return try {
            user.reauthenticate(credential).await()
            user.updatePassword(newPassword).await()
            AuthResult.Success
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResult.Error("Old password is not correct")
        }
    }
}