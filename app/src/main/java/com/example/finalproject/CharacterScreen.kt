package com.example.finalproject

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.finalproject.data.characterAttributes
import com.example.finalproject.data.characterClasses
import com.example.finalproject.data.characterRaces

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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CharacterTextField(
    @StringRes placeholder: Int,
    @StringRes label: Int,
    modifier: Modifier = Modifier,
    isAttr: Boolean = false,
    attrConstraint: Int = 20) {

    //Save the state of the text box's contents.
    var text by remember {mutableStateOf("")}
    var txtStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start)
    var kbOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done)
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    //If the textbox is intended to be for an attribute, center align the text
    if (isAttr) {
        txtStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 32.sp)
        kbOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
    }


    //Create TextField
    TextField(text,
        onValueChange = {
            //Check for non number characters
            if (isAttr) {
                if (it.isEmpty()) {
                    text = ""
                }
                else if (it.matches(Regex("^\\d*\$")) && it.toInt() <= attrConstraint) {
                    text = it
                }
            } else {
                text = it
            }},
        placeholder = { Text(textAlign = TextAlign.Center, text = stringResource(id = placeholder)) },
        label = { Text(text = stringResource(id = label)) },
        modifier = modifier,
        textStyle = txtStyle,
        keyboardOptions = kbOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus() })
    )
}

/**
 * Character Creation Screen
 */
@Composable
fun CharacterScreen(
    title: String,
    modifier: Modifier = Modifier,
    onSubmit: () -> Unit = {}) {

    LazyColumn() {

    item {

        Column(
            modifier = modifier
                .padding(4.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            //Title
            Text(text = title, fontSize = 24.sp)

            //Name
            CharacterTextField(
                placeholder = R.string.placeholder_enter_name,
                label = R.string.label_name
            )

            //Race and Class
            Column(Modifier.padding(8.dp)) {
                CharacterDropdown(
                    items = characterClasses,
                    placeholder = R.string.placeholder_select_class,
                    label = R.string.label_class
                )

                //Spacer
                Spacer(Modifier.padding(4.dp))

                CharacterDropdown(
                    items = characterRaces,
                    placeholder = R.string.placeholder_select_race,
                    label = R.string.label_race
                )
            }

            //Level and XP
            Row(Modifier.fillMaxWidth(1f)) {
                CharacterTextField(
                    placeholder = R.string.placeholder_select_level,
                    label = R.string.label_level,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(.5f),
                    isAttr = true,
                    attrConstraint = 20
                )
                CharacterTextField(
                    placeholder = R.string.placeholder_select_xp,
                    label = R.string.label_XP,
                    modifier = Modifier.padding(8.dp),
                    isAttr = true,
                    attrConstraint = 999999
                )
            }

            //ATTRIBUTES
            for (i in 0..5 step 2) {
                Row(
                    Modifier
                        .fillMaxWidth(1f)
                        .align(Alignment.CenterHorizontally)
                ) {
                    CharacterTextField(
                        placeholder = characterAttributes[i].second,
                        label = characterAttributes[i].first,
                        modifier = Modifier
                            .height(128.dp)
                            .padding(4.dp)
                            .fillMaxWidth(.5f),
                        isAttr = true
                    )
                    CharacterTextField(
                        placeholder = characterAttributes[i + 1].second,
                        label = characterAttributes[i + 1].first,
                        modifier = Modifier
                            .height(128.dp)
                            .padding(4.dp),
                        isAttr = true
                    )
                }
            }

            //DESCRIPTION
            CharacterTextField(
                placeholder = R.string.placeholder_char_desc,
                label = R.string.label_char_desc,
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .height(128.dp)
                )

            //Submit button
            Button(onClick = {}) {
                Text(text = stringResource(id = R.string.button_submit))
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