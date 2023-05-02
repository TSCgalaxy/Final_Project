package com.example.finalproject

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.finalproject.data.ItemEntity

@Composable
fun ItemsMenu(
    modifier: Modifier = Modifier,
    viewModel: ItemMenuViewModel,

    ) {
    val itemState = viewModel.state
    val selectedItems = viewModel.selectedItems
    val inventory = itemState.inventory
    inventory.forEach { selectedItems.add(it.itemID) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(text = "Items List", style = MaterialTheme.typography.h4)

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {
            items(itemState.items.size) { i ->

                val isSelected =
                    remember { mutableStateOf(selectedItems.contains(itemState.items[i].id)) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isSelected.value = !isSelected.value
                            if (isSelected.value) {
                                viewModel.addSelectedItems(itemState.items[i])
                            } else {
                                viewModel.removeSelectedItems(itemState.items[i])
                            }
                        }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text(
                            text = itemState.items[i].name,
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = " ${itemState.items[i].level}")
                    }


                    if (isSelected.value) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = Color.Green,
                            modifier = modifier.size(24.dp)
                        )
                        //viewModel.addItem(selected)
                    }

                }
                Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.LightGray)


            }
        }

    }
}
