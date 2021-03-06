package com.example.composerecipeapp.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composerecipeapp.ui.Dispatch
import com.example.composerecipeapp.ui.MultipleDispatch
import com.example.composerecipeapp.viewmodel.user_interest.Cuisine

@ExperimentalFoundationApi
@Composable
fun CuisineList(
    cuisines: List<Cuisine>,
    modifier: Modifier = Modifier,
    selectionCount: Int = 0,
    dispatcher: MultipleDispatch<Boolean, Cuisine>
) {
    val selectionCount = remember {
        mutableStateOf(selectionCount)
    }
    LazyVerticalGrid(cells = GridCells.Adaptive(120.dp), modifier = modifier) {
        itemsIndexed(cuisines) { _, cuisine ->
            CuisineChip(
                text = cuisine.name,
                isSelected = cuisine.isSelected,
                selectionCount.value
            ) {
                if (it) selectionCount.value++ else selectionCount.value--
                dispatcher(it, cuisine)
            }
        }
    }
}

@Composable
fun CuisineChip(
    text: String,
    isSelected: Boolean,
    selectionCount: Int,
    dispatcher: Dispatch<Boolean>
) {
    SelectableChip(label = text, contentDescription = "", selected = isSelected) {
        if ((selectionCount < 5) || (selectionCount >= 5 && !it)) {
            dispatcher(it)
        }
    }
}
