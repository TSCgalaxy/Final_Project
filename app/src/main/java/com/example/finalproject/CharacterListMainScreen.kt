package com.example.finalproject

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.finalproject.data.*
import kotlinx.coroutines.launch
import java.util.*

class PieChart(context: Context, attrs: AttributeSet) : View(context, attrs){

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun mainCharacterListScreen(
    modifier: Modifier = Modifier,
    onCharacterButtonClicked: () -> Unit,
    viewmodel : CharacterViewModel
){
    val NPCs by viewmodel.conversionUIModel.collectAsState()
    Scaffold(
        bottomBar  = {}
    )
    {
        LazyColumn(modifier = Modifier.background(MaterialTheme.colors.background)) {
        for (x in NPCs.subs) {
            item {

                var info = CharacterInfo(name = x.name, maxHealth = x.maxHP, health = x.currentHP, imageResourceId = R.drawable.dice)
                Button(onClick = onCharacterButtonClicked,) {
                    CharacterItem(info = info)
                }
            }
        }
        items(characterList) {
                Button(onClick = onCharacterButtonClicked,) {
                    CharacterItem(info = it)

                }
            }
        }
    }
}

/**
 * Composable that displays a list item containing a dog icon and their information.
 *
 * @param dog contains the data that populates the list item
 * @param modifier modifiers to set to this composable
 */
@Composable
fun CharacterItem(info: CharacterInfo, modifier: Modifier = Modifier) {
    Card(
        elevation = 4.dp,
        modifier = modifier.padding(1.dp)
            .clip(RoundedCornerShape(50))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            CharacterInfo(info.imageResourceId)
            CharacterInformation(info.name, info.health, info.maxHealth)
        }
    }
}

@Composable
fun CharacterInfo(@DrawableRes dogIcon: Int, modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .size(64.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(50)),
        contentScale = ContentScale.Crop,
        painter = painterResource(dogIcon),
        /*
         * Content Description is not needed here - image is decorative, and setting a null content
         * description allows accessibility services to skip this element during navigation.
         */
        contentDescription = null
    )
}

@Composable
fun CharacterInformation(dogName: String, health: Int, maxHealth: Int, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = dogName,
            style = MaterialTheme.typography.h4,
            modifier = modifier.padding(top = 8.dp)
        )
        Text(
            text = "$health / $maxHealth",
            style = MaterialTheme.typography.h5
        )
    }
}
