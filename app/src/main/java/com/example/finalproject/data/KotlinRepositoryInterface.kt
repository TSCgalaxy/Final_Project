package com.example.finalproject.data

import kotlinx.coroutines.flow.Flow

interface KotlinRepositoryInterface {
    fun getAllCharacters(): Flow<List<CharacterEntity>>
    suspend fun insertCharacter(conversion: CharacterEntity)
    suspend fun insertItem(conversion: ItemEntity)
    suspend fun insertInventory(conversion: CharacterEntity, itemEntity: ItemEntity)
}
