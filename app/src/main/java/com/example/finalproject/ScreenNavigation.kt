package com.example.finalproject

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import com.example.finalproject.data.*
import androidx.navigation.compose.*
import androidx.navigation.navArgument

/**
 * Enum class that will hold all the screens in the app.
 */
enum class DnDScreen(@StringRes val title: Int, val route: String) {
    HomeScreen(title = R.string.main_screen, route = "home"),
    CharacterScreen(title = R.string.character_creator, route = "character"),
    DiceRoller(title = R.string.dice_roller, route = "dice"),
    ProfileScreen(title = R.string.profile_screen, route = "profile/{id}"),
    ItemScreen(title = R.string.item_screen, route = "item/{id}"),
}

/**
 * Composable that will add the app bar to the app.
 * @param navController: NavController that will handle the navigation
 * @param startDestination: The start destination of the app
 * @param modifier: Modifier to apply to the composable
 */
@Composable
fun DndAppBar(
    currentScreen: DnDScreen,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier,
        content = {
            Spacer(modifier = modifier.padding(horizontal = 32.dp))
            IconButton(onClick = {
                navController.navigate(DnDScreen.CharacterScreen.route)
            }) {
                Image(
                    painter = painterResource(R.drawable.addcharacter),
                    contentDescription = null
                )
            }
            Spacer(modifier = modifier.padding(horizontal = 40.dp))
            Spacer(modifier = modifier.padding(horizontal = 40.dp))
            IconButton(onClick = {
                navController.navigate(DnDScreen.DiceRoller.route)
            },
                modifier = Modifier.testTag("dice")) {
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
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = remember(navBackStackEntry) {
        DnDScreen.values().firstOrNull { screen ->
            navBackStackEntry?.destination?.route == screen.route
        } ?: DnDScreen.HomeScreen
    }

    Scaffold(
        bottomBar = {
            DndAppBar(
                currentScreen = currentScreen,
                navController = navController
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState() //

        val context = LocalContext.current
        var database by remember { mutableStateOf(CharacterDB.getInstance(context)) }
        var repository by remember { mutableStateOf(RepositoryClass(database.characterDAO())) }
        repository.getAllItem()


        NavHost(
            navController = navController,
            startDestination = DnDScreen.HomeScreen.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = DnDScreen.HomeScreen.route) {
                mainCharacterListScreen(onCharacterButtonClicked = { id ->
                    navController.navigate(route = "profile/$id")
                    Log.d("CharListScreenNav", "id: $id")
                }, viewmodel = CharacterViewModel(repository))
            }
            composable(
                route = DnDScreen.ProfileScreen.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                Log.d("CharProfScreenNav", "id: $id")
                CharacterProfileScreen(
                    characterViewModel = CharacterProfileViewModel(repository, id),
                    navController = navController
                )
            }
            composable(
                route = DnDScreen.ItemScreen.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                ItemsMenu(
                    viewModel = ItemMenuViewModel(repository, id),
                )
            }
            composable(route = DnDScreen.CharacterScreen.route) {
                CharacterScreen(title = "Create a Character", repo = repository, onGoBack = {
                    navController.navigate(DnDScreen.HomeScreen.route)
                    navController.clearBackStack(DnDScreen.HomeScreen.route)
                })
            }
            composable(route = DnDScreen.DiceRoller.route) {
                DiceRollingScreen(
                    onDiceChanged = { dice: DiceObject, ask: Boolean ->
                        viewModel.updateDiceToRoll(
                            dice,
                            ask
                        )
                    },
                    onRollDice = { viewModel.rollAllDice() },
                    diceViewModel = viewModel
                )
            }

        }

    }
}