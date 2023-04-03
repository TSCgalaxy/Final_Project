package com.example.finalproject

import androidx.compose.runtime.Composable
import dev.chauvin.dicetray.core.dice.Die
import dev.chauvin.dicetray.core.dice.Face
import dev.chauvin.dicetray.core.dice.createDie

@Composable
fun DiceRollingScreen(

){
    val d4 = Die.d4() // This creates a four-sided die with face values ranging from 1 to 4
    val d6 = Die.d6() // This creates a six-sided die with face values ranging from 1 to 6
    val d8 = Die.d8() // This creates an eight-sided die with face values ranging from 1 to 8
    val d10 = Die.d10() // This creates a ten-sided die with face values ranging from 1 to 10
    val d12 = Die.d12() // This creates a twelve-sided die with face values ranging from 1 to 12
    val d20 = Die.d20() // This creates a twenty-sided die with face values ranging from 1 to 20


}