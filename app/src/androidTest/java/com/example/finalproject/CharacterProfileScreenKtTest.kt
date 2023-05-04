package com.example.finalproject

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.room.Room
import com.example.finalproject.data.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * Test for the ItemsMenuScreen
 */
class CharacterProfileScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController
    private lateinit var dao: CharacterDao
    private lateinit var db: CharacterDB


    /**
     * Set up the database
     */
    @Before
    fun setUp() {
        navController = TestNavHostController(composeTestRule.activity)
        db = Room.inMemoryDatabaseBuilder(
            composeTestRule.activity.applicationContext,
            CharacterDB::class.java
        ).allowMainThreadQueries().build()
        dao = db.characterDAO()
    }

    /**
     * Close the database
     */
    @After
    fun tearDown() {
        db.close()
    }


    /**
     * Test the CharacterProfileScreen
     */
    @OptIn(DelicateCoroutinesApi::class)
    @Test
    fun characterProfileScreen_verifyContent() {
        val repo = RepositoryClass(dao)

        val character = CharacterEntity(
            2, "Bob", 1, 2, 2, 2, 3, 5, 7, 6, 8, 4, R.drawable.d4_1, "Celeric", "Halfling"
        )


        GlobalScope.launch {
            dao.addCharacter(character)
        }

        val characterViewModel = CharacterProfileViewModel(repo, 2)
        composeTestRule.setContent {
            CharacterProfileScreen(
                navController = navController,
                characterViewModel = characterViewModel
            )
        }

        composeTestRule.onNodeWithText("Level: 1").assertExists()
        composeTestRule.onNodeWithText("Bob").assertExists()
        composeTestRule.onNodeWithText("Race: Halfling").assertExists()
        composeTestRule.onNodeWithText("Class: Celeric").assertExists()
        composeTestRule.onNodeWithText("Strength: 3").assertExists()
        composeTestRule.onNodeWithText("Dexterity: 5").assertExists()
        composeTestRule.onNodeWithText("Constitution: 4").assertExists()
        composeTestRule.onNodeWithText("Int: 6").assertExists()
        composeTestRule.onNodeWithText("Wisdom: 7").assertExists()
        composeTestRule.onNodeWithText("Charisma: 8").assertExists()


    }

    /**
     * Test the CharacterProfileScreen
     */
    @Test
    fun inventoryDisplayTest() {
        val repo = RepositoryClass(dao)
        val viewModel = CharacterProfileViewModel(repo, 2)

        composeTestRule.setContent {
            InventoryDisplay(
                navController = navController,
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("Add").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Add").performClick()


    }
}