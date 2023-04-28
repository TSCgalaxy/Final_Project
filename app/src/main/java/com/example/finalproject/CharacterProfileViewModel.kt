package com.example.finalproject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * ViewModel for the Character Profile
 * @param repo the repository to get the data from
 * @param id the id of the character
 */
class CharacterProfileViewModel (
    private val repo : RepositoryClass,
    id: Int
) : ViewModel(){

    var state by mutableStateOf(CharacterState())
        private set

    var isDialogOpen by mutableStateOf(false)
    var isItemDialogueOpen by mutableStateOf(false)

    init {
        getCharacterById(id)
        getInventory(id)
        //getItems()
    }

    /**
     * Get all the characters from the DB
     */
    fun getCharacter(){
        viewModelScope.launch {
            repo.getAllCharacters().collectLatest {
                state = state.copy(
                    characters = it
                )
            }
        }
    }

    /**
     * Get a character by id
     * @param id the id of the character
     */
    fun getCharacterById(id: Int){
        viewModelScope.launch {
            repo.getCharacter(id)?.collectLatest {
                state = state.copy(
                    character = it
                )
            }
        }
    }

    /**
     * Delete a character from the DB
     * @param character the character to delete
     */
    fun deleteCharacter(character: CharacterEntity){
        viewModelScope.launch {
            repo.removeCharacter(character)
        }
    }


    /**
     * get all items specific to a character
     * @param charid the id of the character
     */
    fun getInventory(charid: Int){
        viewModelScope.launch {
            repo.getInventory(charid).collectLatest {
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

    /**
     * Add an item to the DB
     * @param item the item to add
     */
    fun addItem(item: ItemEntity) = viewModelScope.launch {
        repo.insertItem(item)
        addInventory(InventoryEntity(state.character!!.id, item.id))

    }

    /**
     * Delete an item from the DB
     * @param item the item to delete
     */
    fun deleteItem(item: ItemEntity) = viewModelScope.launch {
        repo.removeItem(item)
    }

    /**
     * Get all items from the DB
     */
    fun getItems(){
        viewModelScope.launch {
            repo.getAllItem().collectLatest {
                state = state.copy(
                    items = it
                )
            }
        }
    }

    /**
     * Function to open the dialog
     */
    fun openDialog() {
        isDialogOpen = true
    }

    /**
     * Function to close the dialog
     */
    fun closeDialog() {
        isDialogOpen = false
    }

    /**
     * Function to open the Add Item dialog
     */
    fun openItemDialog() {
        isItemDialogueOpen = true
    }

    /**
     * Function to close the Add Item dialog
     */
    fun closeItemDialog() {
        isItemDialogueOpen = false
    }

    /**
     * Update a character in the DB
     * @param character the character to update
     */
    fun updateCharacter(character: CharacterEntity) = viewModelScope.launch {
        repo.updateNPC(character)
    }

    /**
     * Add 1 to the current HP of a character
     * @param it the character to heal
     */
    fun healCharacter(it: CharacterEntity) {
        it.currentHP = it.currentHP + 1
        updateCharacter(it)
    }

    /**
     * Subtract 1 from the current HP of a character
     * @param it the character to damage
     */
    fun damageCharacter(it: CharacterEntity) {
        it.currentHP = it.currentHP - 1
        updateCharacter(it)
    }

}