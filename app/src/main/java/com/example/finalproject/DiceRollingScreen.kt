package com.example.finalproject

import android.widget.ImageButton
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
import dev.chauvin.dicetray.core.dice.Face
import dev.chauvin.dicetray.core.dice.createDie
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chauvin.dicetray.dsl.roll
import java.util.Collections.list

@Composable
fun DiceRollingScreen(
    modifier: Modifier = Modifier,

){
    // These may be better as an array!!
    val d4 = Die.d4() // This creates a four-sided die with face values ranging from 1 to 4
    val d6 = Die.d6() // This creates a six-sided die with face values ranging from 1 to 6
    val d8 = Die.d8() // This creates an eight-sided die with face values ranging from 1 to 8
    val d10 = Die.d10() // This creates a ten-sided die with face values ranging from 1 to 10
    val d12 = Die.d12() // This creates a twelve-sided die with face values ranging from 1 to 12
    val d20 = Die.d20() // This creates a twenty-sided die with face values ranging from 1 to 20

    val dice = listOf (
        Pair(d4,false),
        Pair(d6,false),
        Pair(d8, false),
        Pair(d10, false),
        Pair(d12,false),
        Pair(d20,false)
        )

    val diceToRoll = listOf(
        d4,
        d6,
        d8
    )
    val diceImages = listOf<Int>(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6
    )

    //Scrollable list to display all the dice available to choose from.
    LazyRow( modifier = modifier
    ){
        items(diceImages) {Int->
            DiceCard(Int)

        }
    }

    //Column to add the dice being rolled to
    Column(
        modifier = modifier

    ) {

        var sixResult by remember{ mutableStateOf(d4.roll(1)) }



        sixResult = d4.roll()
    }

    Button(onClick = { }) {
        Text(stringResource(R.string.roll))
    }
}

/**
 * Dice list is used to create a standard image for each dice within the top row of the app.
 * @param Die<Int> dice is the current object to display an image at the top bar.
 */
@Composable
fun DiceCard(diceImage:Int, modifier:Modifier = Modifier){
    IconButton(onClick = { }) {
        Image(
            painter = painterResource(diceImage),
            contentDescription = "Dice",
            modifier = Modifier.
            height(160.dp),
            contentScale = ContentScale.Crop,
            )
    }

}

/**
 * Preview function to see how the dice appear within the top bar
 */
@Preview
@Composable
private fun DiceCardPreview() {
   DiceCard(R.drawable.dice_1)
}