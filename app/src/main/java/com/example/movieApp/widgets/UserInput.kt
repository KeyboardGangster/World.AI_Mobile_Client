package com.example.movieApp.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
fun TextInput(modifier: Modifier, singleLine: Boolean, value: String, label: String, errorMsg: String, predicate: (String) -> Boolean, onValueChange: (String) -> Unit): Boolean {
    val isValid = predicate.invoke(value)

    OutlinedTextField(
        value = value,
        singleLine = singleLine,
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
fun <T> SelectInput(
    modifier: Modifier,
    genreItems: List<ListItemSelectable<T>>,
    label: String,
    errorMsg: String,
    predicate: (List<ListItemSelectable<T>>) -> Boolean,
    onValueChange: (ListItemSelectable<T>) -> Unit
): Boolean {
    val isValid = predicate.invoke(genreItems)

    Text(
        modifier = modifier.padding(top = 4.dp),
        text = label,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h6
    )

    if (!isValid) Text(errorMsg, color = Color.Red)

    LazyRow {
        items(genreItems) { genreItem ->
            Chip(modifier = Modifier.padding(2.dp), colors = ChipDefaults.chipColors(
                backgroundColor = if (genreItem.isSelected) colorResource(id = R.color.purple_200)
                else colorResource(id = R.color.white)
            ), onClick = { onValueChange.invoke(genreItem) }) {
                Text(text = genreItem.reference.toString())
            }
        }
    }

    return isValid
}