package com.example.frichat.data.mapper

import com.example.frichat.data.model.UserDTO
import com.example.frichat.domain.model.User

object UserMapper {
    fun mapToDomain(userDTO: UserDTO): User {
        return User(
            uid = userDTO.uid,
            username = userDTO.username,
            email = userDTO.email,
            profilePicture = userDTO.pp
        )
    }
}