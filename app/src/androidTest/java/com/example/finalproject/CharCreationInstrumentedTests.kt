package com.example.finalproject

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.finalproject.ui.theme.FinalProjectTheme
import onNodeWithStringId
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharCreationInstrumentedTests {

    private lateinit var navController: TestNavHostController
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Before
    fun setupNavController() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            FinalProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DndApp()

                }
            }
        }
    }

    //Verify that given no input to the character creation screen yields an error.
    @Test
    fun testNoInputFail() {

        // navigate to the character creation screen
        composeTestRule.onNodeWithTag("add_character", useUnmergedTree = true).performClick()

        //Scroll to the bottom of the screen and click the button to submit.
        composeTestRule.onNodeWithText("Add Character", useUnmergedTree = true).performScrollTo()
        composeTestRule.onNodeWithText("Add Character", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Add Character", useUnmergedTree = true).performClick()

        //Check that the character has not gone through.
        composeTestRule.onNodeWithText("Add Character", useUnmergedTree = true).assertIsDisplayed()
    }


    //Verify that completing all fields in the creation process adds a character to the database.
    @Test
    fun testAllFieldsValid() {

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
        composeTestRule.onNodeWithText("Add Character").assertDoesNotExist()
    }

    //Verify that all relevant fields accept inputs within their respective limits.
    @Test
    fun testInputBoundaries() {

        // navigate to the character creation screen
        composeTestRule.onNodeWithTag("add_character", useUnmergedTree = true).performClick()


        //Enter maxHP
        composeTestRule.onNodeWithStringId(R.string.label_max_hp).performClick()
        //Type '51'
        composeTestRule.onNodeWithStringId(R.string.label_max_hp).performTextInput("5")
        composeTestRule.onNodeWithStringId(R.string.label_max_hp).performTextInput("1")

        //Check the input has been properly clipped to '5' since the maximum hp is 50.
        composeTestRule.onNodeWithTag("maxHP").assertTextEquals("Maximum HP", "5")


        //Enter XP
        composeTestRule.onNodeWithStringId(R.string.label_XP).performClick()

        //Type a very big number
        composeTestRule.onNodeWithStringId(R.string.label_XP).performTextInput("9")
        composeTestRule.onNodeWithStringId(R.string.label_XP).performTextInput("9")
        composeTestRule.onNodeWithStringId(R.string.label_XP).performTextInput("9")
        composeTestRule.onNodeWithStringId(R.string.label_XP).performTextInput("9")
        composeTestRule.onNodeWithStringId(R.string.label_XP).performTextInput("9")
        composeTestRule.onNodeWithStringId(R.string.label_XP).performTextInput("9")
        composeTestRule.onNodeWithStringId(R.string.label_XP).performTextInput("9")
        composeTestRule.onNodeWithStringId(R.string.label_XP).performTextInput("9")

        //Check the input has been properly clipped to '999999'
        composeTestRule.onNodeWithTag("xp").assertTextEquals("XP", "999999")


        //Enter Level
        composeTestRule.onNodeWithStringId(R.string.label_level).performClick()

        //Type invalid characters
        composeTestRule.onNodeWithStringId(R.string.label_level).performTextInput("^")
        composeTestRule.onNodeWithStringId(R.string.label_level).performTextInput("-")
        composeTestRule.onNodeWithStringId(R.string.label_level).performTextInput("$")
        composeTestRule.onNodeWithStringId(R.string.label_level).performTextInput("*")
        composeTestRule.onNodeWithStringId(R.string.label_level).performTextInput("0")



        //Check the input has been properly clipped to '999999'
        composeTestRule.onNodeWithTag("level").assertTextEquals("Level", "0")

    }

}