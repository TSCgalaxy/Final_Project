package com.example.finalproject

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
import org.junit.Assert

import org.junit.Before
import org.junit.Rule
import org.junit.Test


class DiceScreenTest {

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
        navController.assertCurrentRouteName(DnDScreen.HomeScreen.route)
    }

    @Test
    fun dndNavHost_verifyNavigationToDice() {
        navigateToDiceScreen()
        navController.assertCurrentRouteName(DnDScreen.DiceRoller.route)

    }
    @Test
    fun dndDiceScreen_4SidedSelected() {
        navigateToDiceScreen()
        composeTestRule.onNodeWithTag("4").performClick()
    }

    @Test
    fun dndDiceScreen_6SidedSelected() {
        navigateToDiceScreen()
        composeTestRule.onNodeWithTag("6").performClick()
    }

    @Test
    fun dndDiceScreen_8SidedSelected() {
        navigateToDiceScreen()
        composeTestRule.onNodeWithTag("8").performClick()
    }

    @Test
    fun dndDiceScreen_10SidedSelected() {
        navigateToDiceScreen()
        composeTestRule.onNodeWithTag("10").performClick()
    }


    @Test
    fun dndDiceScreen_12SidedSelected() {
        navigateToDiceScreen()
        composeTestRule.onNodeWithTag("12").performClick()
    }

    @Test
    fun dndDiceScreen_20SidedSelected() {
        navigateToDiceScreen()
        composeTestRule.onNodeWithTag("20").performClick()
    }

    @Test
    fun dndDiceScreen_RolledCorrectly() {
        navigateToDiceScreen()
        composeTestRule.onNodeWithTag("6").performClick()
        composeTestRule.onNodeWithTag("Roll").performClick()


    }

    private fun navigateToDiceScreen() {
        composeTestRule.onNodeWithTag("dice").performClick()
    }

    private fun NavController.assertCurrentRouteName(expectedRouteName: String) {
        Assert.assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
    }
}