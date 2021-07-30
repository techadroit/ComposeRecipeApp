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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composerecipeapp.R
import com.example.composerecipeapp.ui.Dispatch
import com.example.composerecipeapp.ui.MultipleDispatch
import com.example.composerecipeapp.ui.navigation.NavigationDirections
import com.example.composerecipeapp.ui.theme.UserInterestComposable
import com.example.composerecipeapp.ui.theme.primaryColorDark
import com.example.composerecipeapp.ui.views.CuisineList
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
                Text(
                    modifier = Modifier.align(Alignment.TopStart),
                    text = stringResource(id = R.string.select_cuisine),
                    style = MaterialTheme.typography.h1.copy(color = primaryColorDark)
                )
                CuisineList(
                    cuisines = state.cuisines,
                    modifier = Modifier.align(Alignment.Center),
                ){ it,cuisine ->
                    viewModel.dispatch(
                        if (it)
                            SelectedCuisine(cuisine)
                        else
                            RemoveCuisine(cuisine)
                    )
                }
                if (state.enableNextOptions)
                    NextButton(
                        modifier = Modifier.align(Alignment.BottomEnd)
                    ) {
                        viewModel.dispatch(UserInterestSelected)
                    }
            }
        }
    }

    state.sideEffect?.data?.let {
        navController.navigate(NavigationDirections.mainDestination.destination) {
            popUpTo(NavigationDirections.userInterest.destination) {
                inclusive = true
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.dispatch(LoadSupportedCuisine)
    }
}

@Composable
fun NextButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
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

@Preview
@Composable
fun PreviewUserInterest() {

}
