package com.example.finalproject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.CharacterDao
import com.example.finalproject.data.CharacterEntity
import com.example.finalproject.data.ItemEntity
import com.example.finalproject.data.RepositoryClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CharacterProfileScreenViewModel(
    private val repo : RepositoryClass
) : ViewModel() {

    val items = repo.getAllItems()
    var isDialogOpen by mutableStateOf(false)
    var isItemDialogueOpen by mutableStateOf(false)
    var item by mutableStateOf(ItemEntity(0, "", 1))


    fun addItem(item: ItemEntity) = viewModelScope.launch {
        repo.insertItem(item)
    }

    fun deleteItem(item: ItemEntity) = viewModelScope.launch {
        repo.deleteItem(item)
    }

    fun getItem(id: Int) = viewModelScope.launch {
        //item = repo.getItem(id)
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


}