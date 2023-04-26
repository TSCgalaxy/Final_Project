package com.example.finalproject.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the Characters and their inventory Items
 */
@Dao
interface CharacterDao {
    // Insert a character into the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCharacter(player: CharacterEntity)

    //Return a list of all Characters in the DB
    @Query("SELECT * FROM character")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    // Item access queries here
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(item: ItemEntity)

    @Query("SELECT * FROM item")
    fun getAllItems(): Flow<List<ItemEntity>>

    @Delete
    suspend fun deleteItem(item: ItemEntity)

    @Update
    suspend fun updateCharacterHP(player: CharacterEntity)

    @Query("SELECT * FROM character WHERE id =:id")
    fun getCharacter(id: Int): Flow<CharacterEntity>
}