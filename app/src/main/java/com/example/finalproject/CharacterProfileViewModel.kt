package com.example.finalproject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.CharacterState
import com.example.finalproject.data.RepositoryClass
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterProfileViewModel (
    private val repo : RepositoryClass
        ) : ViewModel(){

    var state by mutableStateOf(CharacterState())
        private set

    init {
        getCharacter()
    }

    private fun getCharacter(){
        viewModelScope.launch {
            repo.getAllCharacters().collectLatest {
                state = state.copy(
                     characters = it
                )
            }
        }
    }

}