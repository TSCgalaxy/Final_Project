package com.example.finalproject

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ItemMenuViewModel(
    private val repo: RepositoryClass,
    id: Int
) : ViewModel() {

    val characterId = id
    val selectedItems = mutableSetOf<Int>()

    var state by mutableStateOf(ItemState())
        private set


    init {
        getAllItems()
        getInventory(characterId)
    }

    fun getInventory(id: Int) {
        viewModelScope.launch {
            repo.retrieveInventory(id).collectLatest {
                state = state.copy(
                    inventory = it
                )
            }
        }
    }

    fun addSelectedItems(item: ItemEntity) {
        state = state.copy(
            selectedItems = state.selectedItems + item
        )
        viewModelScope.launch {
            addInventory(InventoryEntity(characterId, item.id))

        }
        for (i in state.selectedItems) {
            Log.d("ItemMenuViewModel", " ${i.name}")
        }
    }

    fun removeSelectedItems(item: ItemEntity) {
        state = state.copy(
            selectedItems = state.selectedItems - item
        )
        viewModelScope.launch {
            repo.removeInventory(InventoryEntity(characterId, item.id))
        }
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
     * Add Inventory to the DB
     * @param inventory the inventory to add
     */
    fun addInventory(inventory: InventoryEntity) = viewModelScope.launch {
        repo.insertInventory(inventory)
    }

}

