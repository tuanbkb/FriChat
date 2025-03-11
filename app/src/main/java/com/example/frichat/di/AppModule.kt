package com.example.frichat.di

import com.example.frichat.data.repository.AuthRepositoryImpl
import com.example.frichat.data.repository.UserRepositoryImpl
import com.example.frichat.domain.repository.AuthRepository
import com.example.frichat.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
        userRepository: UserRepository
    ): AuthRepository =
        AuthRepositoryImpl(firebaseAuth, firestore, userRepository)

    @Provides
    @Singleton
    fun provideUserRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): UserRepository =
        UserRepositoryImpl(firebaseAuth, firestore)
}