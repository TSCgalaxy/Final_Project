package com.example.finalproject

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.finalproject.data.DiceObject
import com.example.finalproject.data.DiceUiState

@Composable
fun DiceRollingScreen(
    modifier: Modifier = Modifier,
    diceViewModel: DiceViewModel,
    onDiceChanged: (DiceObject, Boolean) -> Unit = { dice: DiceObject, ask: Boolean -> Unit},
    onRollDice: () -> Unit = { -> Unit}
){
    val context = LocalContext.current

    // These may be better as an array!!
    val d4:DiceObject = DiceObject(Die.d4(),-1, R.drawable.d4_1) // This creates a four-sided die with face values ranging from 1 to 4
    val d6:DiceObject = DiceObject(Die.d6(),-1, R.drawable.d6_0) // This creates a six-sided die with face values ranging from 1 to 6
    val d8:DiceObject = DiceObject(Die.d8(),-1, R.drawable.d8_1) // This creates an eight-sided die with face values ranging from 1 to 8
    val d10:DiceObject = DiceObject(Die.d10(),-1, R.drawable.d10_1) // This creates a ten-sided die with face values ranging from 1 to 10
    val d12:DiceObject = DiceObject(Die.d12(), -1, R.drawable.dice_1) // This creates a twelve-sided die with face values ranging from 1 to 12
    val d20:DiceObject = DiceObject(Die.d20(),-1, R.drawable.d20_0) // This creates a twenty-sided die with face values ranging from 1 to 20

    val dice = listOf (
        d4,
        d6,
        d8,
        d10,
        d12,
        d20
        )

    val uiState by diceViewModel.uiState.collectAsState()


    //var rollers by  mutableStateListOf(diceUiState.diceToRoll.observeAsState())

    var rollers = uiState.diceToRoll
    var counter by remember { mutableStateOf(0) }
    var ShowPopup by remember { mutableStateOf(false) }


    //Column to add the dice being rolled to
    Column(
        modifier = Modifier
            .background(
                MaterialTheme.colors.background
            )
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Scrollable list to display all the dice available to choose from.
        LazyRow(
            modifier = modifier
        ) {
            items(dice) { it ->
                DiceCard(it, modifier, onDiceChanged)
            }
        }
        Divider(thickness = 5.dp, modifier = modifier.padding(bottom = 16.dp))
        Button(
            onClick = {
                if (!ShowPopup) {
                    onRollDice()
                };ShowPopup = !ShowPopup;counter += 1
            },
            modifier = Modifier.testTag("Roll")
        )
        {
            if (!ShowPopup) {
                Text(stringResource(R.string.roll))
            } else {
                Text("Dismiss")
            }
        }

        Divider(thickness = 5.dp, modifier = modifier.padding(bottom = 16.dp))

        middleDice(rollers = rollers, onDiceChanged = onDiceChanged)

        Divider(thickness = 5.dp, modifier = modifier.padding(bottom = 16.dp))
        if (ShowPopup) {
            PopupWindowDialog(rollers = rollers)
        }
    }
}
/**
 * Dice list is used to create a standard image for each dice within the top row of the app.
 * @param Die<Int> dice is the current object to display an image at the top bar.
 *
 *
 */
@Composable
fun DiceCard(obj: DiceObject, modifier:Modifier = Modifier,
                onDiceChanged: (DiceObject, Boolean) -> Unit = { dice:DiceObject, select: Boolean -> Unit}){

    IconButton(onClick = {onDiceChanged(obj, true)}, modifier = Modifier.testTag(obj.dice.faces.size.toString())) {
        Image(
            painter = painterResource(obj.getImage()),
            contentDescription = "Dice",
            modifier = Modifier
                .height(80.dp)
                .width(80.dp),
            contentScale = ContentScale.Crop,
            )
    }

}

/**
 * middleDice is a function that creates and shows the dice within the middle grid to be rolled and
 * images to be changed.
 *
 * @param List<DiceObject> rollers is the list of dice that are being chosen by the user
 * @param onDiceChanged (DiceObject,Boolean) -> Unit is the function sent to the method to add or
 *  remove dice from the grid.
 */
@Composable
fun middleDice(rollers: List<DiceObject>, onDiceChanged: (DiceObject, Boolean) -> Unit){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
        items(rollers) {item ->
            val diceImage by remember { mutableStateOf(item) }
            IconButton(onClick = { onDiceChanged(item, false)}, modifier = Modifier.testTag(item.dice.faces.size.toString())) {
                Image(
                    painter = painterResource(diceImage.currentImage),
                    contentDescription = "Dice",
                    modifier = Modifier
                            .height(100.dp)
                            .width(100.dp)
                )
            }
        }
    }
}

/**
 * PopupWindowDialog is a method to create a popup window that appears at the bottom of the screen and shows
 * the user what the total outcome is of the dice that they rolled per group of dice Ex.(4 sided, 6 sided...)
 *
 * @param List<DiceObject> rollers is the list of dice that were rolled last.
 */
@Composable
fun PopupWindowDialog(rollers: List<DiceObject>) {
    var d4Total = 0
    var d6Total: Int = 0
    var d8Total: Int = 0
    var d10Total: Int = 0
    var d12Total: Int = 0
    var d20Total: Int = 0
    rollers.forEach {item ->
         if( item.dice.faces.size == 4)
         {
             d4Total += item.Lastroll
         }
        else if (item.dice.faces.size == 6)
         {
            d6Total += item.Lastroll
         }
        else if (item.dice.faces.size == 8)
         {
            d8Total += item.Lastroll
         }
        else if (item.dice.faces.size == 10)
         {
            d10Total += item.Lastroll
         }
        else if (item.dice.faces.size == 12)
         {
            d12Total += item.Lastroll
         }
        else
        {
            d20Total += item.Lastroll
        }
    }
    Popup(
        alignment = Alignment.BottomCenter,
        offset = IntOffset(0, -100)

    ) {
        Box(
                Modifier
                        .size(300.dp, 300.dp)
                        .padding(top = 5.dp)
                        .background(color = MaterialTheme.colors.primaryVariant, RoundedCornerShape(10.dp))
                        .border(1.dp, color = Color.Black, RoundedCornerShape(10.dp))
        ) {
            Column() {
                if (d4Total > 0)
                {
                    Text(
                            text = " 4 Sided Total: " + d4Total.toString(),
                            style = MaterialTheme.typography.h1,
                            fontSize = 30.sp,
                            modifier = Modifier.padding(start = 15.dp)
                    )
                }
                if (d6Total > 0)
                {
                    Text(
                            text = " 6 Sided Total: " + d6Total.toString(),
                            style = MaterialTheme.typography.h1,
                            fontSize = 30.sp,
                            modifier = Modifier.padding(start = 15.dp)
                    )
                }
                if (d8Total > 0)
                {
                    Text(
                            text = " 8 Sided Total: " + d8Total.toString(),
                            style = MaterialTheme.typography.h1,
                            fontSize = 30.sp,
                            modifier = Modifier.padding(start = 15.dp)
                    )
                }
                if (d10Total > 0)
                {
                    Text(
                            text = " 10 Sided Total: " + d10Total.toString(),
                            style = MaterialTheme.typography.h1,
                            fontSize = 30.sp,
                        modifier = Modifier.padding(start = 15.dp)
                    )
                }
                if (d12Total > 0)
                {
                    Text(
                            text = " 12 Sided Total: " + d12Total.toString(),
                            style = MaterialTheme.typography.h1,
                            fontSize = 30.sp,
                        modifier = Modifier.padding(start = 15.dp)
                    )
                }
                if (d20Total > 0)
                {
                    Text(
                            text = " 20 Sided Total: " + d20Total.toString(),
                            style = MaterialTheme.typography.h1,
                            fontSize = 30.sp,
                        modifier = Modifier.padding(start = 15.dp)
                    )
                }
            }
        }
    }

}