package com.example.finalproject

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.finalproject.data.DiceObject
import com.example.finalproject.data.DiceUiState
import dev.chauvin.dicetray.core.dice.Die
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DiceViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(DiceUiState())
    val uiState: StateFlow<DiceUiState> = _uiState.asStateFlow()

    fun updateDiceToRoll(dice: DiceObject, selected: Boolean, ){
        var tempList = _uiState.value.diceToRoll.toMutableList()


        if (selected)
        {
            tempList.add(dice)

        }
        else{
            tempList.remove(dice)

        }

        _uiState.update  {currentState ->
            currentState.copy (
                diceToRoll = tempList,

                )

        }

    }
}