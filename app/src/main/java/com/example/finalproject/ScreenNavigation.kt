package com.example.finalproject

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

enum class DnDScreen(@StringRes val title: Int) {
    HomeScreen(title = R.string.main_screen),
    CharacterScreen(title = R.string.character_creator),
    DiceRoller(title = R.string.dice_roller)
}
/**
 * Composable that will control navigation of what screen the
 * user is currently on.
 */
@Composable
fun DndApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()

    //Get the current screen for the user
    val currentScreen = DnDScreen.valueOf(
        backStackEntry?.destination?.route ?: DnDScreen.HomeScreen.name
    )
}