package com.example.composerecipeapp.ui.user_interest

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composerecipeapp.ui.Dispatch
import com.example.composerecipeapp.ui.navigation.NavigationDirections
import com.example.composerecipeapp.ui.theme.UserInterestComposable
import com.example.composerecipeapp.ui.views.SelectableChip
import com.example.composerecipeapp.util.observeState
import com.example.composerecipeapp.viewmodel.user_interest.*

@ExperimentalFoundationApi
@Composable
fun UserInterest(navController: NavController) {
    val viewModel: UserInterestViewModel = hiltViewModel()
    val state = viewModel.observeState()

    UserInterestComposable(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                CuisineList(
                    cuisines = state.cuisines,
                    modifier = Modifier.align(Alignment.Center),
                    viewModel = viewModel
                )
                if (state.enableNextOptions)
                    NextButton(
                        navController = navController,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
            }
        }
    }
    LaunchedEffect(true){
        viewModel.dispatch(LoadSupportedCuisine)
    }
}

@Composable
fun NextButton(navController: NavController, modifier: Modifier) {
    Button(
        onClick = {
            navController.navigate(NavigationDirections.mainDestination.destination) {
                popUpTo(NavigationDirections.userInterest.destination) {
                    inclusive = true
                }
            }
        },
        modifier = modifier,
        colors =
        ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onSurface
        )
    ) {
        Text("Next", style = MaterialTheme.typography.body1)
    }
}

@ExperimentalFoundationApi
@Composable
fun CuisineList(
    cuisines: List<Cuisine>,
    modifier: Modifier = Modifier,
    viewModel: UserInterestViewModel
) {
    val selectionCount = remember {
        mutableStateOf(0)
    }
    LazyVerticalGrid(cells = GridCells.Adaptive(120.dp), modifier = modifier) {
        itemsIndexed(cuisines) { _, cuisine ->
            CuisineChip(
                text = cuisine.name,
                isSelected = cuisine.isSelected,
                selectionCount.value
            ) {
                if (it) selectionCount.value++ else selectionCount.value--
                viewModel.dispatch(
                    if (it)
                        SelectedCuisine(cuisine)
                    else
                        RemoveCuisine(cuisine)
                )
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

@Preview
@Composable
fun PreviewUserInterest() {

}
