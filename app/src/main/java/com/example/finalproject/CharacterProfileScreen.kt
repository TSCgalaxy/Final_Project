package com.example.finalproject

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.finalproject.R


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
    strength: Int,
    dexterity: Int,
    constitution: Int,
    intelligence: Int,
    wisdom: Int,
    charisma: Int,
) {
    val stats = listOf(
        "Strength: $strength",
        "Dexterity: $dexterity",
        "Constitution: $constitution",
        "Intelligence: $intelligence",
        "Wisdom: $wisdom",
        "Charisma: $charisma"
    )
    var isDialogOpen by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Level: ",
                style = MaterialTheme.typography.h4
            )
            OutlinedButton(
                onClick = { isDialogOpen = true },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                border = BorderStroke(2.dp, Color.White),
            ) {
                Text(text = "Current HP / Max HP", style = MaterialTheme.typography.h5)
            }
        }
        if (isDialogOpen) {
            CustomDialogue(
                onDismiss = { isDialogOpen = false },
                onConfirm = { /*TODO*/ }
            )
        }
        Text(text = "Stats", style = MaterialTheme.typography.h4)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(stats.size) {
                Text(text = stats[it], style = MaterialTheme.typography.h5)
            }
        }
        Text(text = "Inventory", style = MaterialTheme.typography.h4)
        Column() {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { /*TODO*/ },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        modifier = modifier.size(30.dp),
                        tint = Color.Red
                    )

                }
                Text(text = "Item Name", style = MaterialTheme.typography.h5)

            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    modifier = modifier.size(30.dp),
                    tint = Color.Green
                )
            }

        }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { /*TODO*/ },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(text = "Delete", style = MaterialTheme.typography.h5)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomDialogue(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
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
                    text = "Current HP / Max HP",
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
                        onClick = { onConfirm() },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                    ) {
                        Text(text = "Heal", style = MaterialTheme.typography.h5)
                    }

                    Button(
                        onClick = { onDismiss() },
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

@Preview
@Composable
fun CharacterProfileScreenPreview() {
    //CharacterProfileScreen()
}