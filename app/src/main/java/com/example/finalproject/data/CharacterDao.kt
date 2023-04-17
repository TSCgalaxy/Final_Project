package com.example.finalproject.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
    @Query("SELECT * FROM tableplayer")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    // Item access queries here
    // ...
}