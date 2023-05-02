package com.example.finalproject.data

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import androidx.room.Database
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.launch
/**
 * This database describes a Dungeons and Dragons character.
 * The character has various attributes and possesses an inventory of items.
 */
@Database(
    entities = [(CharacterEntity::class), (ItemEntity::class), (InventoryEntity::class)], version = 5, exportSchema = false)

abstract class CharacterDB: RoomDatabase() {
    // Data Access Object
    abstract fun characterDAO(): CharacterDao

    // Companion Object (for singleton pattern)
    companion object {
        private var instance: CharacterDB? = null

        // Get Singleton instance of the database
        var rdc: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                Log.d("DB", "insert")
                db.execSQL("INSERT INTO item VALUES(0, \"Dagger\", \"1 d 4\");")
                db.execSQL("INSERT INTO item VALUES(1, \"Staff\", \"1 d 6\");")
                db.execSQL("INSERT INTO item VALUES(2, \"Food\", \"1 unit of food ration\");")
                db.execSQL("INSERT INTO item VALUES(3, \"Wine\", \"12 oz of wine\");")
                db.execSQL("INSERT INTO item VALUES(4, \"Ale\", \"12 oz of ale\");")
                db.execSQL("INSERT INTO item VALUES(5, \"Mead\", \"12 oz of mead\");")
                db.execSQL("INSERT INTO item VALUES(6, \"Axe\", \"1 d 8\");")
                db.execSQL("INSERT INTO item VALUES(7, \"Raptior\", \"1 d 6\");")
                db.execSQL("INSERT INTO item VALUES(8, \"Throwing Axe\", \"1 d 4\");")
                db.execSQL("INSERT INTO item VALUES(9, \"Orb\", \"Spell Focus\");")
                db.execSQL("INSERT INTO item VALUES(10, \"Book\", \"Ummm... just a book\");")
                db.execSQL("INSERT INTO item VALUES(11, \"Mimic Book\", \"Ummmm... not a book\");")
                db.execSQL("INSERT INTO item VALUES(12, \"Letter\", \"A letter from someone\");")
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                Log.d("DB", "Open")
            }
        }

        fun getInstance(context: Context): CharacterDB {
            Log.d("DB", "$instance")
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context = context,
                    klass = CharacterDB::class.java,
                    name = "character_db"
                ).addCallback(rdc)
                    .build()
            }
            //Return the DB instance
            Log.d("DB", "Returning")
            return instance as CharacterDB
        }
    }
}