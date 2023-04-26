package com.example.finalproject.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//
/**
 * Represents a D&D item.
 */
@Entity (tableName = "item")
class ItemEntity (
    @PrimaryKey (autoGenerate = false)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "Modifiers")
    val level: String,
)