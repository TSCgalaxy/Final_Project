package com.example.finalproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory")
class InventoryEntity(
    @PrimaryKey()
    val characterID: Int = 0,
    @PrimaryKey()
    val itemID: Int = 0
)
