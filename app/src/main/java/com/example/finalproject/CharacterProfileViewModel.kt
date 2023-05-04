package com.example.finalproject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.CharacterEntity
import com.example.finalproject.data.CharacterState
import com.example.finalproject.data.InventoryEntity
import com.example.finalproject.data.ItemEntity
import com.example.finalproject.data.RepositoryClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the CharacterProfileScreen
 * @param repo the repository to get the data from
 * @param id the id of the character to get
 */
class CharacterProfileViewModel (
    private val repo : RepositoryClass,
    id: Int
) : ViewModel(){

    var state = MutableStateFlow(CharacterState())
        private set

    private var characterId = id
    var isDialogOpen by mutableStateOf(false)


    init {
        getCharacterById(characterId)
        getInventory(characterId)
    }


    /**
     * Function to get all the characters from the DB
     * @param id the id of the character to get
     */
    fun getCharacterById(id: Int){
        viewModelScope.launch {
            repo.getCharacter(id)?.collectLatest {char ->
                state.update{it.copy(character = char)}
            }
        }
    }


    /**
     * Function to delete a character from the DB
     * @param character the character to delete
     */
    fun deleteCharacter(character: CharacterEntity){
        viewModelScope.launch {
            repo.removeCharacter(character)
        }
    }


    /**
     * Function to get all the items from the DB
     * @param charid the id of the character to get the items for
     */
    fun getInventory(charid: Int){
        viewModelScope.launch {
            repo.getInventory(charid).collectLatest { ite ->
                state.update{it.copy(items = ite)}
            }
        }
    }


    /**
     * Function to add an item to the DB
     * @param item the item to add
     */
    fun deleteItem(item: ItemEntity) = viewModelScope.launch {
        //repo.removeItem(item)
        repo.removeInventory(InventoryEntity(characterId, item.id))
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
     * Function to update a character to the DB
     * @param character the characetr to update
     */
    fun updateCharacter(character: CharacterEntity) = viewModelScope.launch {
        repo.updateNPC(character)
    }

    /**
     * Function to add 1 to the current HP of a character
     * @param it the character to heal
     */
    fun healCharacter(it: CharacterEntity) {
        it.currentHP = it.currentHP + 1
        updateCharacter(it)
    }

    /**
     * Function to subtract 1 from the current HP of a character
     * @param it the character to damage
     */
    fun damageCharacter(it: CharacterEntity) {
        it.currentHP = it.currentHP - 1
        updateCharacter(it)
    }

}