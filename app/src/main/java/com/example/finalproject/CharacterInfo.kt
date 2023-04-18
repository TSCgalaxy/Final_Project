package com.example.finalproject

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CharacterInfo(@DrawableRes val imageResourceId: Int,
                         val name: String,
                         val health: Int,
                         val maxHealth: Int
                         )
val characterList = listOf(
    CharacterInfo(R.drawable.ic_launcher_background, R.string.name_1.toString(), 2,8),
    CharacterInfo(R.drawable.dice, R.string.name_2.toString(), 16, 20),
    CharacterInfo(R.drawable.addcharacter, R.string.name_3.toString(), 2, 10),
    CharacterInfo(R.drawable.dice, R.string.name_4.toString(), 8, 6),
    CharacterInfo(R.drawable.ic_launcher_foreground, R.string.name_5.toString(), 8, 13),
)