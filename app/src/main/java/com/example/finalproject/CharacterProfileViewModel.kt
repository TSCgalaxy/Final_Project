package com.example.finalproject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.CharacterEntity
import com.example.finalproject.data.CharacterState
import com.example.finalproject.data.ItemEntity
import com.example.finalproject.data.RepositoryClass
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterProfileViewModel (
    private val repo : RepositoryClass,
    private val id: Int
        ) : ViewModel(){

    var state by mutableStateOf(CharacterState())
        private set

    var isDialogOpen by mutableStateOf(false)
    var isItemDialogueOpen by mutableStateOf(false)

    init {
        getCharacterById(id)
        //getInventory(id)
        getItems()
    }

     fun getCharacter(){
        viewModelScope.launch {
            repo.getAllCharacters().collectLatest {
                state = state.copy(
                     characters = it
                )
            }
        }
    }

     fun getCharacterById(id: Int){
        viewModelScope.launch {
            repo.getCharacter(id)?.collectLatest {
                state = state.copy(
                    character = it
                )
            }
        }
    }

    fun deleteCharacter(character: CharacterEntity){
        viewModelScope.launch {
            repo.removeCharacter(character)
        }
    }

    fun getInventory(charid: Int){
        viewModelScope.launch {
            repo.getInventory(charid).collectLatest {
                state = state.copy(
                    items = it
                )
            }
        }
    }

    fun addItem(item: ItemEntity) = viewModelScope.launch {
        repo.insertItem(item)

    }

    fun deleteItem(item: ItemEntity) = viewModelScope.launch {
        repo.removeItem(item)
    }

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

    fun updateCharacter(character: CharacterEntity) = viewModelScope.launch {
        repo.updateNPC(character)
    }

    fun healCharacter(it: CharacterEntity) {
        it.currentHP = it.currentHP + 1
        updateCharacter(it)
    }

    fun damageCharacter(it: CharacterEntity) {
        it.currentHP = it.currentHP - 1
        updateCharacter(it)
    }

}