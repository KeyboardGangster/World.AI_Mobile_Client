package com.example.movieApp.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.movieApp.R
import com.example.movieApp.models.ListItemSelectable


@Composable
fun TextInput(modifier: Modifier, value: String, label: String, errorMsg: String, predicate: (String) -> Boolean, onValueChange: (String) -> Unit): Boolean {
    val isValid = predicate.invoke(value)

    OutlinedTextField(
        value = value,
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        onValueChange = onValueChange,
        label = { Text(text = label) },
        isError = !isValid
    )

    if (!isValid)
        Text(errorMsg, color = Color.Red)

    return isValid
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectInput(
    modifier: Modifier,
    genreItems: List<ListItemSelectable>,
    label: String,
    errorMsg: String,
    predicate: (List<ListItemSelectable>) -> Boolean,
    onValueChange: (ListItemSelectable) -> Unit
): Boolean {
    val isValid = predicate.invoke(genreItems)

    Text(
        modifier = modifier.padding(top = 4.dp),
        text = label,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.h6
    )

    if (!isValid) Text(errorMsg, color = Color.Red)

    LazyHorizontalGrid(
        modifier = Modifier.height(100.dp), rows = GridCells.Fixed(3)
    ) {
        items(genreItems) { genreItem ->
            Chip(modifier = Modifier.padding(2.dp), colors = ChipDefaults.chipColors(
                backgroundColor = if (genreItem.isSelected) colorResource(id = R.color.purple_200)
                else colorResource(id = R.color.white)
            ), onClick = { onValueChange.invoke(genreItem) }) {
                Text(text = genreItem.title)
            }
        }
    }

    return isValid
}