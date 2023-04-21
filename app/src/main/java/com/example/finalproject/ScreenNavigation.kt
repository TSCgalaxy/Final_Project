package com.example.finalproject

import android.annotation.SuppressLint
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
import com.example.finalproject.data.*
import kotlinx.coroutines.launch

enum class DnDScreen(@StringRes val title: Int) {
    HomeScreen(title = R.string.main_screen),
    CharacterScreen(title = R.string.character_creator),
    DiceRoller(title = R.string.dice_roller)
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
            if(currentScreen == DnDScreen.CharacterScreen || currentScreen == DnDScreen.DiceRoller)
            {
                IconButton(onClick = { navController.navigate(DnDScreen.HomeScreen.name) }) {
                    Image(
                        painter = painterResource(R.drawable.d20_0),
                        contentDescription = null
                    )
                }
            }
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
        val coroutineScope = rememberCoroutineScope()
        val entity: CharacterEntity = CharacterEntity(0,"0",0,0,0,0,0,0,0,0,0,0,"0", "0")
        val item0 = ItemEntity(id = 0, name = "Dagger", level = "1 d 4")
        val item1 = ItemEntity(id = 1, name = "Staff", level = "1 d 6")
        val item2 = ItemEntity(id = 2, name = "Food", level = "1 unit of food ration")
        val item3 = ItemEntity(id = 3, name = "Wine", level = "12 oz of wine")
        val item4 = ItemEntity(id = 4, name = "Ale", level = "12 oz of ale")
        val item5 = ItemEntity(id = 5, name = "Mead", level = "12 oz of mead")
        val item6 = ItemEntity(id = 6, name = "Axe", level = "1 d 8")
        val item7 = ItemEntity(id = 7, name = "Raptior", level = "1 d 6")
        val item8 = ItemEntity(id = 8, name = "Throwing Axe", level = "1 d 4")
        val item9 = ItemEntity(id = 9, name = "Orb", level = "spell focus")
        val item10 = ItemEntity(id = 10, name = "Book", level = "ummm... just a book")
        val item11 = ItemEntity(id = 11, name = "Mimic Book", level = "Ummmm... not a book")
        val item12 = ItemEntity(id = 12, name = "Letter", level = "A letter from someone")
        coroutineScope.launch {
            repository.insertCharacter(entity)
            repository.insertItem(item0)
            repository.insertItem(item1)
            repository.insertItem(item2)
            repository.insertItem(item3)
            repository.insertItem(item4)
            repository.insertItem(item5)
            repository.insertItem(item6)
            repository.insertItem(item7)
            repository.insertItem(item8)
            repository.insertItem(item9)
            repository.insertItem(item10)
            repository.insertItem(item11)
            repository.insertItem(item12)
        }
        NavHost(
            navController = navController,
            startDestination = DnDScreen.HomeScreen.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = DnDScreen.HomeScreen.name) {
                mainCharacterListScreen(onCharacterButtonClicked = {}, viewmodel =  CharacterViewModel(repository))
            }
            composable(route = DnDScreen.CharacterScreen.name) {
                CharacterScreen(title = "Create a Character", repo = repository)
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