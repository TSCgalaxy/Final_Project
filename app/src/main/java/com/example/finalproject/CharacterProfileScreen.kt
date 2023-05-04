package com.example.finalproject

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.os.Environment.DIRECTORY_DOCUMENTS
import android.provider.DocumentsContract
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.finalproject.data.ItemEntity
import java.io.File
import java.io.FileOutputStream


/**
 * This is the character profile screen. It displays the character's name, level, and stats.
 * @param modifier Modifier to set to this composable
 * @param characterViewModel The view model to get the character data
 */
@Composable
fun CharacterProfileScreen(
    modifier: Modifier = Modifier,
    characterViewModel: CharacterProfileViewModel,
    navController: NavController,
) {
    val characterState = characterViewModel.state

    //Box(modifier = modifier.fillMaxWidth()) {
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
            Text(text = "${characterState.character?.name}", style = MaterialTheme.typography.h4)
        }
        Text(
            text = "Level: ",
            style = MaterialTheme.typography.h4
        )
        OutlinedButton(
            onClick = { characterViewModel.openDialog() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            border = BorderStroke(2.dp, Color.White),
        ) {
            Text(text = "Current HP / Max HP", style = MaterialTheme.typography.h5)
        }

        if (characterViewModel.isDialogOpen) {
            CustomDialogue(
                onDismiss = { characterViewModel.closeDialog() },
                viewModel = characterViewModel,
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
            viewModel = characterViewModel,
        )

        if (characterViewModel.isItemDialogueOpen) {
            AddItemUI(
                onDismiss = { characterViewModel.closeItemDialog() },
                addItem = { item -> characterViewModel.addItem(item) }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        //PAYTON ADDED just incase I break it
        var test by rememberSaveable { mutableStateOf(false)}
        //var test = false
        Button(
            onClick = { test = true },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ){
            if(test){
                createPDF(characterViewModel)
                test = false
                Log.d("working", "working")
                Log.d("working", "working")
                Log.d("working", "working")
                Log.d("working", "working")
                Log.d("working", "working")
            }
            Text(text = "Create PDF", style = MaterialTheme.typography.h5)
        }
        //
        Button(
            onClick = {
                characterState.character?.let { characterViewModel.deleteCharacter(character = it) }
                navController.popBackStack()
            },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
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
 * @param viewModel the view model to add the item to
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomDialogue(
    onDismiss: () -> Unit,
    viewModel: CharacterProfileViewModel
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
                    text = "Current HP = ${viewModel.state.character?.currentHP}",
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
                        onClick = {
                            viewModel.state.character?.let { viewModel.healCharacter(it) }
                            onDismiss()
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                    ) {
                        Text(text = "Heal", style = MaterialTheme.typography.h5)
                    }

                    Button(
                        onClick = {
                            viewModel.state.character?.let { viewModel.damageCharacter(it) }
                            onDismiss()
                        },
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
 * @param addItem function to add the item to the view model
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
    val characterState = viewModel.state
    Column(
        modifier = modifier
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Strength: ${characterState.character?.attStr}", modifier = Modifier.weight(1f), style = MaterialTheme.typography.h6)
            Text(text = "Charisma: ${characterState.character?.attChr}", modifier = Modifier.weight(1f), style = MaterialTheme.typography.h6)
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Wisdom: ${characterState.character?.attWis}", modifier = Modifier.weight(1f), style = MaterialTheme.typography.h6)
            Text(text = "Detrexity: ${characterState.character?.attDex}", modifier = Modifier.weight(1f), style = MaterialTheme.typography.h6)
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Constitution: ${characterState.character?.attCon}", modifier = Modifier.weight(1f), style = MaterialTheme.typography.h6)
            Text(text = "Int: ${characterState.character?.attInt}", modifier = Modifier.weight(1f), style = MaterialTheme.typography.h6)
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
    viewModel: CharacterProfileViewModel,
) {
    val itemState = viewModel.state

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
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
/**
    creates a pdf
    @param viewModel character viewmodel to export data to pdf
 */
@Composable
fun createPDF(viewModel: CharacterProfileViewModel) {
    var document = PdfDocument()
    var  file = File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS), "Charcter.pdf")

    // create a page description
    var width : Int = (8 * 72) as Int
    var length : Int = (11 * 72) as Int
    var pageInfo = PdfDocument.PageInfo.Builder(width, length, 1).create()

    // start a page
    var page = document.startPage(pageInfo)

    // draw something on the page
    var canva = page.canvas
    var title: Paint = Paint()
    title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
    val bitmap = BitmapFactory.decodeResource(LocalContext.current.resources, ((viewModel.state.character?.image)!!.toInt()))//getDrawable(LocalContext.current.resources, , null)
    val scale = 0.5f // Scale factor

    val scaledBitmap = Bitmap.createScaledBitmap(
        bitmap,
        (bitmap.getWidth() * scale).toInt(),
        (bitmap.getHeight() * scale).toInt(),
        false
    )
    title.textSize = 15F

    title.setColor(ContextCompat.getColor(LocalContext.current, R.color.black))
    //var image = bitmapFactory
    canva.drawText("${viewModel.state.character?.race}, ${viewModel.state.character?.charClass}", 50F, 100F, title)
    canva.drawText("${viewModel.state.character?.name}", 50F, 80F, title)
    canva.drawText("Level: ${viewModel.state.character?.level}, XP: ${viewModel.state.character?.XP}", 50F, 120F, title)
    canva.drawText("HP: ${viewModel.state.character?.currentHP}/ ${viewModel.state.character?.maxHP}", 200F, 120F, title)

    title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
    title.setColor(ContextCompat.getColor(LocalContext.current, R.color.black))
    title.textSize = 15F
    canva.drawText("Strength: ${viewModel.state.character?.attStr} \t Dexterity: ${viewModel.state.character?.attDex}", 50F, 150F, title)
    canva.drawText("Constitution: ${viewModel.state.character?.attCon} \t Intelligence: ${viewModel.state.character?.attInt}", 50F, 165F, title)
    canva.drawText("Wisdom: ${viewModel.state.character?.attWis} \t Charisma: ${viewModel.state.character?.attChr}", 50F, 180F, title)
    canva.drawBitmap(scaledBitmap, 300f, 100f, null)
    //canva.drawText("Description: ${viewModel.state.character?.}", 50F, 180F, title)
    canva.drawText("Inventory:", 50F, 200F, title)
    var format = 215F
    viewModel.state.items.forEach { item ->
        canva.drawText("${item.name}, ${item.level}", 50F, format, title)
        format += 15F
    }


    title.textAlign = Paint.Align.CENTER
    document.finishPage(page)
    document.writeTo(FileOutputStream(file, false))
    document.close()
    Log.d("file", file.toURI().toString())
    Log.d("file", file.toURI().toString())
    Log.d("file", file.toURI().toString())
    Log.d("file", file.toURI().toString())
    Log.d("file", file.toURI().toString())
    Log.d("file", file.toURI().toString())
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        //addCategory(Intent.CATEGORY_OPENABLE)
        type = "application/pdf"

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        putExtra(DocumentsContract.EXTRA_INITIAL_URI, file.toURI())
    }

    LocalContext.current.startActivity(intent)
    //startActivityForResult()
}

@Preview
@Composable
fun CharacterProfileScreenPreview() {
    //CharacterProfileScreen()
}