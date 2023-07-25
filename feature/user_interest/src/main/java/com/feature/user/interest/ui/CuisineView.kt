package com.feature.user.interest.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.domain.common.pojo.Cuisine
import com.feature.common.Dispatch
import com.feature.common.MultipleDispatch
import com.feature.common.ui.common_views.SelectableChip

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@ExperimentalFoundationApi
@Composable
fun CuisineList(
    cuisines: List<Cuisine>,
    modifier: Modifier = Modifier,
    selectionCount: Int = 0,
    spacing: Dp = 8.dp,
    dispatcher: MultipleDispatch<Boolean, Cuisine>
) {
    val selectionCount = remember {
        mutableStateOf(selectionCount)
    }
    val leadingIcon: @Composable () -> Unit = { Icon(Icons.Default.Check, null) }
    FlowRow(
        modifier = modifier, horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        cuisines.forEach {
            FilterChip(
                selected = it.isSelected,
                onClick = {
                    if (!it.isSelected) selectionCount.value++ else selectionCount.value--
                    dispatcher(!it.isSelected, it)
                },
                label = { Text(text = it.name) },
                leadingIcon = (if (it.isSelected) leadingIcon else null)
            )
        }
    }
}

@Composable
fun CuisineChip(
    text: String, isSelected: Boolean, selectionCount: Int, dispatcher: Dispatch<Boolean>
) {
    SelectableChip(label = text, contentDescription = "", selected = isSelected) {
        if ((selectionCount < 5) || (selectionCount >= 5 && !it)) {
            dispatcher(it)
        }
    }
}
