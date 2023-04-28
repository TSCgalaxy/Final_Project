package com.example.finalproject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.ItemEntity
import com.example.finalproject.data.ItemState
import com.example.finalproject.data.RepositoryClass
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ItemsViewModel(
    private val repo : RepositoryClass
) : ViewModel() {

    //val items = repo.getAllItems()
    var isDialogOpen by mutableStateOf(false)
    var isItemDialogueOpen by mutableStateOf(false)


    var state by mutableStateOf(ItemState())
        private set

    init {
        getItems()
    }


    private fun getItems(){
        viewModelScope.launch {
//            repo.getAllItem().collectLatest {
//                state = state.copy(
//                    items = it
//                )
//            }
        }
    }

    fun addItem(item: ItemEntity) = viewModelScope.launch {
        repo.insertItem(item)

    }

    fun deleteItem(item: ItemEntity) = viewModelScope.launch {
        //repo.removeItem(item)
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


}