package com.example.finalproject.data

import androidx.annotation.StringRes
import com.example.finalproject.R

//Classes
val characterClasses: Array<String> = arrayOf(
    "Barbarian",
    "Bard",
    "Cleric",
    "Druid",
    "Fighter",
    "Monk",
    "Paladin",
    "Ranger",
    "Rogue",
    "Sorcerer",
    "Warlock",
    "Wizard")

//Races
val characterRaces: Array<String> = arrayOf(
    "Dragonborn",
    "Dwarf",
    "Elf",
    "Gnome",
    "Half-Elf",
    "Halfling",
    "Half-Orc",
    "Human",
    "Tiefling")

//Attribute Names
val characterAttributes: List<Pair<Int, Int>> = listOf(
    Pair(R.string.attr_strength, R.string.attr_strength_desc),
    Pair(R.string.attr_constitution, R.string.attr_constitution_desc),
    Pair(R.string.attr_dexterity, R.string.attr_dexterity_desc),
    Pair(R.string.attr_intelligence, R.string.attr_intelligence_desc),
    Pair(R.string.attr_wisdom, R.string.attr_wisdom_desc),
    Pair(R.string.attr_charisma, R.string.attr_charisma_desc)
)