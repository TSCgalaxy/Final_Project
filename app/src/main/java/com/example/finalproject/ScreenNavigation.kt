package com.example.finalproject

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.finalproject.data.*
import kotlinx.coroutines.launch

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
            IconButton(onClick = { navController.navigate(DnDScreen.CharacterScreen.name)
                }) {
                Image(
                    painter = painterResource(R.drawable.addcharacter),
                    contentDescription = null
                )
            }
            Spacer(modifier = modifier.padding(horizontal = 40.dp))
            Spacer(modifier = modifier.padding(horizontal = 40.dp))
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
@SuppressLint("CoroutineCreationDuringComposition")
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
        val uiState by viewModel.uiState.collectAsState() //

        val context = LocalContext.current
        var database by remember{ mutableStateOf(CharacterDB.getInstance(context)) }
        var repository by remember{ mutableStateOf(RepositoryClass(database.characterDAO())) }
        repository.getAllItems()
        NavHost(
            navController = navController,
            startDestination = DnDScreen.HomeScreen.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = DnDScreen.HomeScreen.name) {
                mainCharacterListScreen(onCharacterButtonClicked = {}, viewmodel =  CharacterViewModel(repository))
            }
            composable(route = DnDScreen.CharacterScreen.name) {


                CharacterScreen(title = "Create a Character", repo = repository,
                    viewModel = CreateCharViewModel(LocalContext.current),
                    onGoBack = {
                        navController.navigate(DnDScreen.HomeScreen.name)
                        navController.clearBackStack(DnDScreen.HomeScreen.name)
                    },
                )
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