package com.example.finalproject.data

/**
 * Data class to hold the state of the Items in the DB
 */
data class ItemState(
    val items:List<ItemEntity> = emptyList(),
    val selectedItems: List<ItemEntity> = emptyList(),
    val inventory: List<InventoryEntity> = emptyList(),
    val item: ItemEntity? = null,
)
