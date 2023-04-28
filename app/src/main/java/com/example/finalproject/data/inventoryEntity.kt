package com.example.finalproject.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
//
@Entity(tableName = "inventory",
        primaryKeys = ["characterID", "itemID"],
        foreignKeys = [
                ForeignKey(entity = CharacterEntity::class,
                        parentColumns = ["id"],
                        childColumns = ["characterID"],
                        onDelete = ForeignKey.CASCADE),
                ForeignKey(entity = ItemEntity::class,
                        parentColumns = ["id"],
                        childColumns = ["itemID"],
                        onDelete = ForeignKey.CASCADE)
        ]
)

class InventoryEntity(
        val characterID: Int = 0,
        val itemID: Int = 0
)

