package com.example.finalproject

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.finalproject.data.DiceObject

enum class DnDScreen(@StringRes val title: Int) {
    HomeScreen(title = R.string.main_screen),
    CharacterScreen(title = R.string.character_creator),
    DiceRoller(title = R.string.dice_roller),
    ProfileScreen(title = R.string.profile_screen)
}

@Composable
fun DndAppBar(
    currentScreen: DnDScreen,
    navController:NavHostController,
    modifier: Modifier = Modifier
){
    BottomAppBar(
        modifier = modifier,
        content = {
            Spacer(modifier = modifier.padding(horizontal = 32.dp))
            IconButton(onClick = { //navController.navigate(DnDScreen.CharacterScreen.name)
                }) {
                Image(
                    painter = painterResource(R.drawable.addcharacter),
                    contentDescription = null
                )
            }
            Spacer(modifier = modifier.padding(horizontal = 80.dp))
            IconButton(onClick = { navController.navigate(DnDScreen.DiceRoller.name)
            }) {
                Image(
                    painter = painterResource(R.drawable.dice),
                    contentDescription = null
                )
            }
        }
    )

}

/**
 * Composable that will control navigation of what screen the
 * user is currently on.
 */
@Composable
fun DndApp(
    modifier: Modifier = Modifier,
    viewModel: DiceViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    //Get the current screen for the user
    val currentScreen = DnDScreen.valueOf(
        backStackEntry?.destination?.route ?: DnDScreen.HomeScreen.name
    )

    Scaffold(
        bottomBar = {
            DndAppBar(
                currentScreen = currentScreen,
                navController = navController
            )
        }
    ) {innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = DnDScreen.HomeScreen.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = DnDScreen.HomeScreen.name) {
                mainCharacterListScreen(onCharacterButtonClicked = {})
            }
            composable(route = DnDScreen.DiceRoller.name) {
                DiceRollingScreen(
                    onDiceChanged = { dice: DiceObject, ask: Boolean -> viewModel.updateDiceToRoll(dice, ask) },
                    onRollDice = { viewModel.rollAllDice()},
                    diceViewModel = viewModel
                )
            }
        }

    }
}