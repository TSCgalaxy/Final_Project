package com.example.finalproject.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CharacterViewModel(repository:KotlinRepositoryInterface): ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    // Data class to store info like in other view models
    val conversionUIModel: StateFlow<CharactersList> = repository.getAllCharacters()
        .map {
            CharactersList(it)
            // convert Flow to StateFlow
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = CharactersList()
        )
}
//alone
data class CharactersList(
    val subs: List<CharacterEntity> = listOf()
)