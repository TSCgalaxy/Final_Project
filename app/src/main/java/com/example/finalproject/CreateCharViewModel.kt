package com.example.finalproject

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CharacterUIState(
    val name: String = "",
    val xp: Int = 0,
    val level: Int = 0,
    val race: String = "",
    val cClass: String = "",
    val attrStrength: Int = 0,
    val attrConstitution: Int = 0,
    val attrDexterity: Int = 0,
    val attrWisdom: Int = 0,
    val attrIntelligence: Int = 0,
    val attrCharisma: Int = 0,
    val description: String = ""

)
class CreateCharViewModel(context: Context): ViewModel() {
    //State
    private val _uiState = MutableStateFlow(CharacterUIState())
    val uiState: StateFlow<CharacterUIState> = _uiState.asStateFlow()

    fun reset() {
        //Reset ui state here
    }

    //GETTERS

    /**
     * Returns the character's name.
     */
    fun getName(): String {
        return _uiState.value.name
    }

    /**
     * Returns the character's Experience Points
     */
    fun getXP(): Int {
        return _uiState.value.xp
    }

    /**
     * Gets the character's level
     */
    fun getLvl(): Int {
        return _uiState.value.level
    }

    /**
     * Gets the character's Race
     */
    fun getRace(): String {
        return _uiState.value.race
    }

    /**
     * Gets the character's Class
     */
    fun getClass(): String {
        return _uiState.value.cClass
    }

    /**
     * Gets the character's Strength
     */
    fun getStr(): Int {
        return _uiState.value.attrStrength
    }

    /**
     * Gets the character's Dexterity
     */
    fun getDex(): Int {
        return _uiState.value.attrDexterity
    }

    /**
     * Gets the character's Constitution
     */
    fun getCon(): Int {
        return _uiState.value.attrConstitution
    }

    /**
     * Gets the character's Wisdom
     */
    fun getWis(): Int {
        return _uiState.value.attrWisdom
    }

    /**
     * Gets the character's Intelligence
     */
    fun getInt(): Int {
        return _uiState.value.attrIntelligence
    }

    /**
     * Gets the character's Charisma
     */
    fun getChr(): Int {
        return _uiState.value.attrCharisma
    }

    /**
     * Gets the character's Description
     */
    fun getDesc(): String {
        return _uiState.value.description
    }

    //SETTERS

    /**
     * Sets the character's name
     */
    fun setName(value: String) {
        _uiState.update { currentState ->
            currentState.copy(name = value)
        }
    }

    /**
     * Sets the character's XP
     */
    fun setXP(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(xp = value)
        }
    }

    /**
     * Sets the character's level
     */
    fun setLvl(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(level = value)
        }
    }


    /**
     * Sets the character's Class
     */
    fun setClass(value: String) {
        _uiState.update { currentState ->
            currentState.copy(cClass = value)
        }
    }

    /**
     * Sets the character's name
     */
    fun setRace(value: String) {
        _uiState.update { currentState ->
            currentState.copy(race = value)
        }
    }

    /**
     * Sets the character's Strength
     */
    fun setStr(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(attrStrength = value)
        }
    }

    /**
     * Sets the character's Dexterity
     */
    fun setDex(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(attrDexterity = value)
        }
    }

    /**
     * Sets the character's Constitution
     */
    fun setCon(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(attrConstitution = value)
        }
    }

    /**
     * Sets the character's Wisdom
     */
    fun setWis(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(attrWisdom = value)
        }
    }

    /**
     * Sets the character's Intelligence
     */
    fun setInt(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(attrIntelligence = value)
        }
    }

    /**
     * Sets the character's Charisma
     */
    fun setChr(value: Int) {
        _uiState.update { currentState ->
            currentState.copy(attrCharisma = value)
        }
    }

    /**
     * Sets the character's Strength
     */
    fun setDesc(value: String) {
        _uiState.update { currentState ->
            currentState.copy(description = value)
        }
    }
}