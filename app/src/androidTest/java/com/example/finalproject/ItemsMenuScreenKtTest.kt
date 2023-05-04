package com.example.finalproject

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.testing.TestNavHostController
import androidx.room.Room
import androidx.room.withTransaction
import com.example.finalproject.data.CharacterDB
import com.example.finalproject.data.CharacterDao
import com.example.finalproject.data.ItemEntity
import com.example.finalproject.data.RepositoryClass
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Test for the ItemsMenuScreen
 */
class ItemsMenuScreenKtTest {

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
     * Test the ItemsMenuScreen
     */
    @Test
    fun itemsMenuScreen_verifyText() {
        val repo = RepositoryClass(dao)
        val viewModel = ItemMenuViewModel(repo, 1)
        composeTestRule.setContent {
            ItemsMenu(
                viewModel = viewModel
            )
        }
        composeTestRule.onNodeWithText("Items List").assertExists()
    }

    /**
     * Test the ItemsMenuScreen
     */
    @Test
    fun itemsMenuScreen_verifyClick() {
        val repo = RepositoryClass(dao)
        val viewModel = ItemMenuViewModel(repo, 1)
        composeTestRule.setContent {
            ItemsMenu(
                viewModel = viewModel
            )
        }
        val items = listOf(
            ItemEntity(1, "Sword", "1"),
            ItemEntity(2, "Shield", "1"),
            ItemEntity(3, "Bow", "1")
        )

        runBlocking {
            withContext(Dispatchers.IO) {
                db.withTransaction {
                    dao.addItem(items[0])
                    dao.addItem(items[1])
                    dao.addItem(items[2])
                }
            }
        }


        for(item in items){
            composeTestRule.onNodeWithText(item.name).assertExists()
            composeTestRule.onNodeWithText(item.name).performClick()
        }


    }


}