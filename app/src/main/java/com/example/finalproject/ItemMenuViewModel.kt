package com.example.finalproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the ItemMenuScreen
 * @param repo the repository to get the data from
 * @param id the id of the character to get
 */
class ItemMenuViewModel(
    private val repo: RepositoryClass,
    id: Int
) : ViewModel() {

    val characterId = id
    val selectedItems = mutableSetOf<Int>()

    var state = MutableStateFlow(ItemState())
        private set


    init {
        getAllItems()
        getInventoryById(characterId)
    }

    /**
     * Get all the characters from the DB
     * @param id the id of the character to get
     */
    fun getInventoryById(id: Int){
        viewModelScope.launch {
            repo.retrieveInventory(id).collectLatest {char ->
                state.update{it.copy(inventory = char)}
            }
        }
    }

    /**
     * Add selected items to the DB
     * @param item the item to add
     */
    fun addSelectedItems(item: ItemEntity) {
        state.update { inv ->
            inv.copy(
                selectedItems = inv.selectedItems + item
            ) }
        viewModelScope.launch {
            addInventory(InventoryEntity(characterId, item.id))

        }
    }

    /**
     * Remove selected items from the DB
     * @param item the item to remove
     */
    fun removeSelectedItems(item: ItemEntity) {
        state.update {
            it.copy(
                selectedItems = it.selectedItems - item
            )
        }
        viewModelScope.launch {
            repo.removeInventory(InventoryEntity(characterId, item.id))
        }
    }


    /**
     * Get all the characters from the DB
     */
    fun getAllItems(){
        viewModelScope.launch {
            repo.getAllItem().collectLatest {char ->
                state.update{it.copy(items = char)}
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

