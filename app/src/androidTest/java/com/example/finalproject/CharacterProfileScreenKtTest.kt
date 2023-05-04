package com.example.finalproject

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import androidx.room.Room
import com.example.finalproject.data.CharacterDB
import com.example.finalproject.data.CharacterDao
import com.example.finalproject.data.CharacterEntity
import com.example.finalproject.data.RepositoryClass
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.*


class CharacterProfileScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()


    private lateinit var navController: TestNavHostController

    private lateinit var dao: CharacterDao
    private lateinit var db: CharacterDB


    @Before
    fun setUp() {
        navController = TestNavHostController(composeTestRule.activity)
        db = Room.inMemoryDatabaseBuilder(
            composeTestRule.activity.applicationContext,
            CharacterDB::class.java
        ).allowMainThreadQueries().build()
        dao = db.characterDAO()
    }

    @After
    fun tearDown() {
        db.close()
    }



    @Test
    fun characterProfileScreen_verifyContent() {
        val repo = RepositoryClass(dao)

        val character = CharacterEntity(
            2, "Bob", 1, 2, 2, 2, 3, 5, 7, 6, 8, 4, "Halfling", "Celeric"
        )

        GlobalScope.launch {
            dao.addCharacter(character)
        }
        val retrieveChar = dao.getCharacter(2)

        val characterViewModel = CharacterProfileViewModel(repo, 2)
        composeTestRule.setContent {
            CharacterProfileScreen(
                navController = navController,
                characterViewModel = characterViewModel
            )
        }
        val state = characterViewModel.state.value.character
        //assertEquals(retrieveChar,state)
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
}