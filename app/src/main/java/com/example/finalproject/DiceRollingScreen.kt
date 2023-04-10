package com.example.finalproject

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import dev.chauvin.dicetray.core.dice.Die
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.finalproject.data.DiceObject
import com.example.finalproject.data.DiceUiState

@Composable
fun DiceRollingScreen(
    modifier: Modifier = Modifier,
    diceUiState: DiceUiState,
    onDiceChanged: (DiceObject, Boolean) -> Unit = { dice: DiceObject, ask: Boolean -> Unit}
){
    // These may be better as an array!!
    val d4:DiceObject = DiceObject(Die.d4(),-1) // This creates a four-sided die with face values ranging from 1 to 4
    val d6:DiceObject = DiceObject(Die.d6(),-1) // This creates a six-sided die with face values ranging from 1 to 6
    val d8:DiceObject = DiceObject(Die.d8(),-1) // This creates an eight-sided die with face values ranging from 1 to 8
    val d10:DiceObject = DiceObject(Die.d10(),-1) // This creates a ten-sided die with face values ranging from 1 to 10
    val d12:DiceObject = DiceObject(Die.d12(), -1) // This creates a twelve-sided die with face values ranging from 1 to 12
    val d20:DiceObject = DiceObject(Die.d20(),-1) // This creates a twenty-sided die with face values ranging from 1 to 20

    val dice = listOf (
        d4,
        d6,
        d8,
        d10,
        d12,
        d20
        )

    val rollers: MutableList<DiceObject> = mutableListOf()

    for (roll in diceUiState.diceToRoll) {
        rollers.add(roll)
    }


    //Column to add the dice being rolled to
    Column(
        modifier = modifier

    ) {

        //Scrollable list to display all the dice available to choose from.
        LazyRow( modifier = modifier
        ){
            items(dice) { it ->
                DiceCard(it, modifier, onDiceChanged )
            }
        }

        //display dice below row
        rollers.forEach {item ->
            IconButton(onClick = {onDiceChanged(item, false)} ) {
                Image(
                    painter = painterResource(item.getImage()),
                    contentDescription = "Dice",
                    modifier = Modifier.
                    height(160.dp),
                    contentScale = ContentScale.Crop,
                )
            }
        }

        Button(onClick = { }, modifier.align(alignment = Alignment.CenterHorizontally) ) {
            Text(stringResource(R.string.roll))
        }

    }

}

/**
 * Dice list is used to create a standard image for each dice within the top row of the app.
 * @param Die<Int> dice is the current object to display an image at the top bar.
 */
@Composable
fun DiceCard(obj: DiceObject, modifier:Modifier = Modifier,
                onDiceChanged: (DiceObject, Boolean) -> Unit = { dice:DiceObject, select: Boolean -> Unit}){

    IconButton(onClick = {onDiceChanged(obj, true)}) {
        Image(
            painter = painterResource(obj.getImage()),
            contentDescription = "Dice",
            modifier = Modifier.
            height(160.dp),
            contentScale = ContentScale.Crop,
            )
    }

}

