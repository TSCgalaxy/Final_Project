package com.example.finalproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * This database describes a Dungeons and Dragons character.
 * The character has various attributes and possesses an inventory of items.
 */
@Database(entities = [(CharacterEntity::class), (ItemEntity::class)], version = 1, exportSchema = false)
abstract class CharacterDB: RoomDatabase() {
    // Data Access Object
    abstract fun characterDAO(): CharacterDao

    // Companion Object (for singleton pattern)
    companion object {
        private var instance: CharacterDB? = null

        // Get Singleton instance of the database
        fun getInstance(context: Context): CharacterDB {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context = context,
                    klass = CharacterDB::class.java,
                    name = "character_db"
                ).fallbackToDestructiveMigration().build()
            }
            //Return the DB instance
            return instance as CharacterDB
        }
    }
}