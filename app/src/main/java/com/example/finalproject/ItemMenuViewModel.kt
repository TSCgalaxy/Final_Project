package com.example.finalproject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ItemMenuViewModel(
    private val repo : RepositoryClass,
    character: CharacterEntity
) : ViewModel() {


    var state by mutableStateOf(ItemState())
        private set

    var characterState by mutableStateOf(CharacterState(character))
        private set

    init {
        getAllItems()
    }

    /**
     * Get all the characters from the DB
     */
    fun getAllItems() {
        viewModelScope.launch {
            repo.getAllItem().collectLatest {
                state = state.copy(
                    items = it
                )
            }
        }

    }

    /**
     * Delete an item from the DB
     * @param item the item to delete
     */
    fun deleteItem(item: ItemEntity) {
        viewModelScope.launch {
            repo.removeItem(item)
        }
    }

    /**
     * Add Inventory to the DB
     * @param inventory the inventory to add
     */
    fun addInventory(inventory: InventoryEntity) = viewModelScope.launch {
        repo.insertInventory(inventory)
    }

    /**
     * Add an item to the DB
     * @param item the item to add
     */
    fun addItem(item: ItemEntity) = viewModelScope.launch {
        repo.insertItem(item)
        addInventory(InventoryEntity(characterState.character!!.id, item.id))

    }






}

