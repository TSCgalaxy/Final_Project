package com.example.finalproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory", primaryKeys = ["characterID", "itemID"])
class InventoryEntity(

    val characterID: Int = 0,

    val itemID: Int = 0
)
