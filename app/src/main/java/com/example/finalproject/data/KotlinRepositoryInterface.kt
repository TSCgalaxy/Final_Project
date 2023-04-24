package com.example.finalproject.data

import kotlinx.coroutines.flow.Flow

interface KotlinRepositoryInterface {
    fun getAllCharacters(): Flow<List<CharacterEntity>>
    suspend fun insertCharacter(conversion: CharacterEntity)

    suspend fun insertItem(item: ItemEntity)

    suspend fun deleteItem(item: ItemEntity)

    fun getAllItems(): Flow<List<ItemEntity>>
}
