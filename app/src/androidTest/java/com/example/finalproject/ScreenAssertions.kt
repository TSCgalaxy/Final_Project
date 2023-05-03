package com.example.finalproject

import androidx.navigation.NavController
import junit.framework.TestCase.assertEquals

//Asserts whether or not the screen matches the expected input
fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    assertEquals(
        expectedRouteName,
        currentBackStackEntry?.destination?.route
    )
}