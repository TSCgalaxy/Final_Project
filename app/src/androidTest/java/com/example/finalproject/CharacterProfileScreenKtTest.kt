package com.example.finalproject

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import com.example.finalproject.data.CharacterEntity
import com.example.finalproject.data.RepositoryClass
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.*


class CharacterProfileScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()


    private lateinit var navController: TestNavHostController

    @Test
    fun characterProfileScreen_verifyContent() {
        //Create a mock Repository
        val repo = mock(RepositoryClass::class.java)
        //Create a mock character Entity
        val character = CharacterEntity(1,"Bob", 2, 3,5,3,6,7,7,8,9,0,"human","paladin")
        `when`(repo.getCharacter(1)).thenReturn(flowOf(character))
        //Create a mock character view model
        val characterViewModel = CharacterProfileViewModel(repo, 1)

        composeTestRule.setContent {
            CharacterProfileScreen(
                navController = navController,
                characterViewModel = characterViewModel
                )
        }

        //Verify that the character name is displayed
        composeTestRule.onNodeWithText("Bob").assertExists()
        //Find Text that displays level
        composeTestRule.onNodeWithText("Level: 2").assertExists()
        //Verify the stats are displayed
        composeTestRule.onNodeWithText("Strength: 3").assertExists()
        composeTestRule.onNodeWithText("Dexterity: 5").assertExists()
        composeTestRule.onNodeWithText("Constitution: 3").assertExists()
        composeTestRule.onNodeWithText("Intelligence: 6").assertExists()
        composeTestRule.onNodeWithText("Wisdom: 7").assertExists()
        composeTestRule.onNodeWithText("Charisma: 7").assertExists()


    }
}