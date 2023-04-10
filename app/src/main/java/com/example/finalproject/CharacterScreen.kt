package com.example.finalproject

import android.widget.EditText
import android.widget.GridLayout
import android.widget.GridView
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.finalproject.data.characterAttributes
import com.example.finalproject.data.characterClasses
import com.example.finalproject.data.characterRaces
import java.time.format.TextStyle

@Composable
fun TextFieldColorOverride(
    textColor: Color = Color.Black,
    disabledTextColor: Color = Color.Black,
) = TextFieldDefaults.textFieldColors(
    textColor = textColor,
    disabledTextColor = disabledTextColor,
)

@Composable
fun CharacterDropdown (items: Array<String>,
                       @StringRes placeholder: Int,
                       @StringRes label: Int) {
    var isExpanded by remember { mutableStateOf(false) }
    var currentSelectedText by remember { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    //Color override
    val colors: TextFieldColors = TextFieldColorOverride()

    // Decide which icon to use based on whether or not the box is expanded.
    // TODO: Make custom icons?
    val icon = if (isExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    //Create Text Field
    TextField(
        colors = colors,
        enabled = false,
        value = currentSelectedText,
        onValueChange = {/* currentSelectedText = it */},
        placeholder = { Text(text = stringResource(id = placeholder)) },
        label = { Text(text = stringResource(id = label), color = Color.Black)
        },
        //Resize according to the state of the textFieldSize
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coords ->
                textFieldSize = coords.size.toSize()
            }
            .clickable { isExpanded = !isExpanded },
        trailingIcon = {
            Icon(icon, "contentDescription",
                Modifier.clickable { isExpanded = !isExpanded })
        }
    )

    //The dropdown menu itself
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = {isExpanded = false},
        modifier = Modifier.width(
            with(LocalDensity.current) {
                textFieldSize.width.toDp()
            }
        )
    ) {
        // Load in all the items.
        items.forEach {
            label ->
            DropdownMenuItem(onClick = {
                currentSelectedText = label
                isExpanded = false }
            ) {
                //label
                Text(text = label)
            }
        }
    }
}

@Composable
fun CharacterTextField(@StringRes placeholder: Int, @StringRes label: Int) {
    //Save the state of the text box's contents.
    var text by remember {mutableStateOf("")}

    //Create TextField
    TextField(text,
        onValueChange = {text = it},
        placeholder = { Text(text = stringResource(id = placeholder)) },
        label = { Text(text = stringResource(id = label)) },
    )
}


/**
 * Character Creation Screen
 */
@Composable
fun CharacterScreen(title: String,
                    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        //Title
        Text(text = title, fontSize = 24.sp)
        CharacterTextField(placeholder = R.string.placeholder_enter_name, label = R.string.label_name)
        Column(Modifier.padding(8.dp)) {
            CharacterDropdown(items = characterClasses,
                placeholder = R.string.placeholder_select_class,
                label = R.string.label_class)
            CharacterDropdown(items = characterRaces,
                placeholder = R.string.placeholder_select_race,
                label = R.string.label_race )
        }

        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp)) {
            characterAttributes.forEach() {it ->
                item {
                    Text(it)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterScreenPreview() {
    CharacterScreen("Create a Character")
}