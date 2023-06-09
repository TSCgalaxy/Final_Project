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
    suspend fun getAllInventory(conversion: CharacterEntity):  Flow<List<InventoryEntity>>

    suspend fun updateNPC(player: CharacterEntity)
    suspend fun updateInvetory(inventoryEntity: InventoryEntity)
    fun getCharacter(userId: Int): Flow<CharacterEntity>?

    fun retrieveCharacter(id: Int): CharacterEntity?

    fun retrieveInventory(id: Int): Flow<List<InventoryEntity>>
}
//