package com.example.finalproject.data

/**
 * Data class to hold the state of the Characters in the DB
 */
data class CharacterState(
    val character: CharacterEntity = null!!,
    val characters:List<CharacterEntity> = emptyList(),
    val items: List<ItemEntity> = emptyList(),
    val inventory: InventoryEntity? = null,
)