package com.example.finalproject.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the Characters and their inventory Items
 */
@Dao
interface CharacterDao {
    // Insert a character into the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCharacter(player: CharacterEntity)
    @Delete
    suspend fun removeCharacter(player: CharacterEntity)
    @Delete
    suspend fun removeItem(itemEntity: ItemEntity)
    @Delete
    suspend fun removeInventory(inventoryEntity: InventoryEntity)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(itemEntity: ItemEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToInventory(inventoryEntity: InventoryEntity)
    //Return a list of all Characters in the DB
    @Query("SELECT * FROM tableplayer")
    fun getAllCharacters(): Flow<List<CharacterEntity>>
    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateNPC(player: CharacterEntity)
    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateInvetory(inventoryEntity: InventoryEntity)

    @Query("SELECT * FROM item")
    fun getAllItem(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM inventory")
    fun getAllFromInventory(): Flow<List<InventoryEntity>>
    // Item access queries here
    // ...
}