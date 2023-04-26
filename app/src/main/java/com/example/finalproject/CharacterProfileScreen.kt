package com.example.finalproject

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.finalproject.data.CharacterEntity
import com.example.finalproject.data.ItemEntity


/**
 * This is the character profile screen. It displays the character's name, level, and stats.
 * @param modifier Modifier to set to this composable
 * @param strength The character's strength stat
 * @param dexterity The character's dexterity stat
 * @param constitution The character's constitution stat
 * @param intelligence The character's intelligence stat
 * @param wisdom The character's wisdom stat
 * @param charisma The character's charisma stat
 */
@Composable
fun CharacterProfileScreen(
    modifier: Modifier = Modifier,
    ItemViewModel: ItemsViewModel,
    characterViewModel: CharacterProfileViewModel,
) {

    Box(modifier = modifier.fillMaxWidth()) {


    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(
                    id = R.drawable.images
                ),
                contentDescription = null,
                modifier = modifier
                    .clip(CircleShape)
                    .size(140.dp)
            )
            Spacer(modifier = modifier.width(30.dp))
            Text(text = "Name", style = MaterialTheme.typography.h3)
        }
        Text(
            text = "Level: ",
            style = MaterialTheme.typography.h4
        )
        OutlinedButton(
            onClick = { ItemViewModel.openDialog() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            border = BorderStroke(2.dp, Color.White),
        ) {
            Text(text = "Current HP / Max HP", style = MaterialTheme.typography.h5)
        }

        if (ItemViewModel.isDialogOpen) {
            CustomDialogue(
                onDismiss = { ItemViewModel.closeDialog() },
            )
        }
        Text(text = "Stats", style = MaterialTheme.typography.h4)
        StatsDisplay(
            modifier = modifier,
            viewModel = characterViewModel,
        )

        Text(text = "Inventory", style = MaterialTheme.typography.h4)
        //InventoryDisplay
        InventoryDisplay(
            modifier = modifier,
            viewModel = ItemViewModel,
        )

        if (ItemViewModel.isItemDialogueOpen) {
            AddItemUI(
                onDismiss = { ItemViewModel.closeItemDialog() },
                addItem = { item -> ItemViewModel.addItem(item) }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }

    Button(
        onClick = { /*TODO*/ },
        modifier = modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
    ) {
        Text(text = "Delete", style = MaterialTheme.typography.h5)
    }
}
}

/**
 * a custom dialogue that allows the user to add an item to their inventory
 * @param onDismiss function to dismiss the dialogue
 * @param char the character to update its HP
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomDialogue(
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = MaterialTheme.colors.background
        ) {
            Column {
                Text(
                    text = "Current HP = ",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                    ) {
                        Text(text = "Heal", style = MaterialTheme.typography.h5)
                    }

                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text(text = "Damage", style = MaterialTheme.typography.h5)
                    }
                }
            }

        }
    }
}

/**
 * a custom dialogue that allows the user to add an item to their inventory
 * @param onDismiss function to dismiss the dialogue
 * @param modifier modifier for the dialogue
 */
@Composable
fun AddItemUI(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    addItem: (item: ItemEntity) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.background
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                Text(
                    text = "Add new Item",
                    style = MaterialTheme.typography.h6
                )

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Name") },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text

                    )
                )
                TextField(
                    value = level,
                    onValueChange = { level = it },
                    label = { Text(text = "Level") },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number

                    )
                )

                Button(
                    onClick = {
                        onDismiss()
                        val item = ItemEntity(name = name, level = level)
                        addItem(item)
                    }
                ) {
                    Text(text = "Add", style = MaterialTheme.typography.h6)
                }

            }

        }
    }

}

/**
 * a composable that displays the stats of a character
 * @param viewModel the view model to get the stats from
 * @param modifier modifier for the composable
 */
@Composable
fun StatsDisplay(
    modifier: Modifier = Modifier,
    viewModel: CharacterProfileViewModel
) {
    //var viewModel = viewModel<CharacterProfileViewModel>()
    val characterState = viewModel.state
    Column(
        modifier = modifier
    ) {
        characterState.characters.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(100.dp)
            ) {
                rowItems.forEach { item ->
                    // Your composable to display item
                    Text(item.XP.toString())
                }
            }

        }
    }
}

/**
 * a composable that displays the inventory of a character
 * @param modifier modifier for the composable
 * @param viewModel the view model to get the items from
 */
@Composable
fun InventoryDisplay(
    modifier: Modifier = Modifier,
    viewModel: ItemsViewModel
) {
    val itemState = viewModel.state

    Column {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()) {
            IconButton(onClick = { viewModel.openItemDialog() }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    modifier = modifier.size(30.dp),
                    tint = Color.Green
                )
            }
            Text(text = "Add", style = MaterialTheme.typography.h5)
        }
        itemState.items.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Text(text = item.name, style = MaterialTheme.typography.h5)
                Spacer(
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = { viewModel.deleteItem(item) },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        modifier = modifier
                            .size(30.dp),
                        tint = Color.Red
                    )

                }
            }

        }


    }
}

@Preview
@Composable
fun CharacterProfileScreenPreview() {
    //CharacterProfileScreen()
}