package com.example.frichat.data.mapper

import com.example.frichat.data.model.MessageDTO
import com.example.frichat.domain.model.Message
import com.google.firebase.Timestamp

object MessageMapper {
    fun mapToDomain(messageDTO: MessageDTO) : Message {
        return Message(
            senderId = messageDTO.senderId,
            text = messageDTO.text,
            timestamp = messageDTO.timestamp.toDate()
        )
    }

    fun mapToDTO(message: Message) : MessageDTO {
        return MessageDTO(
            senderId = message.senderId,
            text = message.text,
            timestamp = Timestamp(message.timestamp)
        )
    }
}