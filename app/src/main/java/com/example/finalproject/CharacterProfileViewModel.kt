package com.example.finalproject

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    character: CharacterEntity,
) : ViewModel(){

    var state by mutableStateOf(CharacterState(character))
        private set

    var isDialogOpen by mutableStateOf(false)

    val items = repo.getAllItem()



    init {
        getCharacterById(character.id)
        getInventory(character.id)
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