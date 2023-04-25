package com.example.finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.finalproject.data.CharacterDB
import com.example.finalproject.data.RepositoryClass
import com.example.finalproject.ui.theme.FinalProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DndApp()
                    /*val db = CharacterDB.getInstance(this)
                    val repo = RepositoryClass(db.characterDAO())
                    val viewModel = ItemsViewModel(repo)
                    val viewModelChar = CharacterProfileViewModel(repo)

                    CharacterProfileScreen(
                        ItemViewModel = viewModel,
                        characterViewModel = viewModelChar
                    )*/
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}