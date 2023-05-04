package com.example.finalproject

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.finalproject.R
import com.example.finalproject.DnDScreen
import onNodeWithStringId
import org.junit.Assert

import org.junit.Before
import org.junit.Rule
import org.junit.Test


class  CharacterListTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController


    @Before
    fun setupDnDNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            DndApp(navController = navController)
        }
    }

    @Test
    fun dndNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(DnDScreen.HomeScreen.name)
    }

    @Test
    fun dndNavHost_verifyNavigationToProfile() {
        composeTestRule.onNodeWithTag("1").performClick()
        navController.assertCurrentRouteName(DnDScreen.ProfileScreen.route)
    }


    private fun addCharacter() {
        // navigate to the character creation screen
        composeTestRule.onNodeWithTag("add_character", useUnmergedTree = true).performClick()

        //Enter name
        composeTestRule.onNodeWithStringId(R.string.label_name).performClick()
        composeTestRule.onNodeWithStringId(R.string.label_name).performTextInput("Test Character")

        //Enter maxHP
        composeTestRule.onNodeWithStringId(R.string.label_max_hp).performClick()
        composeTestRule.onNodeWithStringId(R.string.label_max_hp).performTextInput("25")

        //Enter level
        composeTestRule.onNodeWithStringId(R.string.label_level).performClick()
        composeTestRule.onNodeWithStringId(R.string.label_level).performTextInput("2")

        //Enter xp
        composeTestRule.onNodeWithStringId(R.string.label_XP).performClick()
        composeTestRule.onNodeWithStringId(R.string.label_XP).performTextInput("325")

        //Enter attributes
        composeTestRule.onNodeWithStringId(R.string.attr_strength).performClick()
        composeTestRule.onNodeWithStringId(R.string.attr_strength).performTextInput("3")

        composeTestRule.onNodeWithStringId(R.string.attr_dexterity).performClick()
        composeTestRule.onNodeWithStringId(R.string.attr_dexterity).performTextInput("3")

        composeTestRule.onNodeWithStringId(R.string.attr_constitution).performClick()
        composeTestRule.onNodeWithStringId(R.string.attr_constitution).performTextInput("3")

        composeTestRule.onNodeWithStringId(R.string.attr_charisma).performClick()
        composeTestRule.onNodeWithStringId(R.string.attr_charisma).performTextInput("3")

        composeTestRule.onNodeWithStringId(R.string.attr_intelligence).performClick()
        composeTestRule.onNodeWithStringId(R.string.attr_intelligence).performTextInput("3")

        composeTestRule.onNodeWithStringId(R.string.attr_wisdom).performClick()
        composeTestRule.onNodeWithStringId(R.string.attr_wisdom).performTextInput("3")


        //Enter Description
        composeTestRule.onNodeWithStringId(R.string.label_char_desc).performClick()
        composeTestRule.onNodeWithStringId(R.string.label_char_desc).performTextInput("A Description")

        //Enter Class and Race
        composeTestRule.onNodeWithStringId(R.string.label_name).performScrollTo()
        composeTestRule.onNodeWithStringId(R.string.label_race).performClick()
        composeTestRule.onNodeWithText("Human").performClick()

        composeTestRule.onNodeWithStringId(R.string.label_class).performClick()
        composeTestRule.onNodeWithText("Bard").performClick()

        composeTestRule.onNodeWithTag("Select an image").performScrollTo()
        composeTestRule.onNodeWithTag("Select an image").performClick()
        composeTestRule.onNodeWithText("Helm").performClick()


        //Submit the character
        composeTestRule.onNodeWithText("Add Character").performScrollTo()
        composeTestRule.onNodeWithText("Add Character").performClick()

        //Ensure that the button no longer appears.
        //composeTestRule.onNodeWithText("Add Character").assertDoesNotExist()
    }

    private fun NavController.assertCurrentRouteName(expectedRouteName: String) {
        Assert.assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
    }
}
