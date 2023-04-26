package com.example.finalproject.data

import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface KotlinRepositoryInterface {
    fun getAllCharacters(): Flow<List<CharacterEntity>>
    suspend fun insertCharacter(conversion: CharacterEntity)
    suspend fun insertItem(conversion: ItemEntity)
    suspend fun insertInventory(conversion: InventoryEntity)
    fun getAllItem(): Flow<List<ItemEntity>>
    suspend fun removeCharacter(player: CharacterEntity)
    suspend fun removeItem(itemEntity: ItemEntity)
    suspend fun removeInventory(inventoryEntity: InventoryEntity)
    fun getAllInventory(conversion: CharacterEntity):  Flow<List<InventoryEntity>>

    fun updateNPC(player: CharacterEntity)
    fun updateInvetory(inventoryEntity: InventoryEntity)
    fun getCharacter(userId: Int): Flow<CharacterEntity>?
}
//