package com.example.finalproject.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a D&D character.
 */
@Entity (tableName = "character")
class CharacterEntity (
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "level")
    val level: Int,

    @ColumnInfo(name = "maxHP")
    val maxHP: Int,

    @ColumnInfo(name = "currentHP")
    val currentHP: Int,

    // Strength
    @ColumnInfo(name = "attStr")
    val attStr: Int,

    // Dexterity
    @ColumnInfo(name = "attDex")
    val attDex: Int,

    // Wisdom
    @ColumnInfo(name = "attWis")
    val attWis: Int,

    // Intelligence
    @ColumnInfo(name = "attInt")
    val attInt: Int,

    // Charisma
    @ColumnInfo(name = "attChr")
    val attChr: Int,


)