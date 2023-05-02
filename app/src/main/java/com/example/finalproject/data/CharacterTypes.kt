package com.example.finalproject.data

import com.example.finalproject.R

//
//Classes
//Name, Optional Image
val characterClasses: List<Pair<String, Int>> = listOf(
    Pair("Barbarian", 0),
    Pair("Bard", 0),
    Pair("Cleric", 0),
    Pair("Druid", 0),
    Pair("Fighter", 0),
    Pair("Monk", 0),
    Pair("Paladin", 0),
    Pair("Ranger", 0),
    Pair("Rogue", 0),
    Pair("Sorcerer", 0),
    Pair("Warlock", 0),
    Pair("Wizard", 0)
)

//Races
//Name, Optional Image
val characterRaces: List<Pair<String, Int>> = listOf(
    Pair("Dragonborn", 0),
    Pair("Dwarf", 0),
    Pair("Elf", 0),
    Pair("Gnome", 0),
    Pair("Half-Elf", 0),
    Pair("Halfling", 0),
    Pair("Half-Orc", 0),
    Pair("Human", 0),
    Pair("Tiefling", 0)
)


//Profile Pictures
//Name, Optional Image
val pfp: List<Pair<String, Int>> = listOf(
    Pair("Dice", R.drawable.dice),
    Pair("Helm", R.drawable.addcharacter)
)

//Attribute Names
//Name, Description
val characterAttributes: List<Pair<Int, Int>> = listOf(
    Pair(R.string.attr_strength, R.string.attr_strength_desc),
    Pair(R.string.attr_constitution, R.string.attr_constitution_desc),
    Pair(R.string.attr_dexterity, R.string.attr_dexterity_desc),
    Pair(R.string.attr_intelligence, R.string.attr_intelligence_desc),
    Pair(R.string.attr_wisdom, R.string.attr_wisdom_desc),
    Pair(R.string.attr_charisma, R.string.attr_charisma_desc)
)