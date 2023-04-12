package com.example.finalproject.data

import kotlinx.coroutines.flow.Flow

interface KotlinRepositoryInterface {
    fun getAllCharacters(): Flow<List<CharacterEntity>>
    suspend fun insertCharacter(conversion: CharacterEntity)
}
