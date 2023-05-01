package com.example.composerecipeapp.ui.user_interest

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.core.navigtion.navigator.NavComposable
import com.example.composerecipeapp.R
import com.example.composerecipeapp.ui.destinations.MainViewIntent
import com.example.composerecipeapp.ui.destinations.UserInterestIntent
import com.example.composerecipeapp.ui.provider.ParentNavHostController
import com.example.composerecipeapp.ui.theme.UserInterestComposable
import com.example.composerecipeapp.ui.theme.primaryColorDark
import com.example.composerecipeapp.ui.views.CuisineList
import com.example.composerecipeapp.util.observeState
import com.example.composerecipeapp.viewmodel.user_interest.*

@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.UserInterestScreen(){
    NavComposable(UserInterestIntent()) {
        UserInterest()
    }
}

@ExperimentalFoundationApi
@Composable
fun UserInterest() {
    val topLevelNavigator = ParentNavHostController.current
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
                    style = MaterialTheme.typography.displayLarge.copy(color = primaryColorDark)
                )
                CuisineList(
                    cuisines = state.cuisines,
                    modifier = Modifier.align(Alignment.Center),
                ) { it, cuisine ->
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

    state.viewEffect?.data?.let {
        topLevelNavigator.navigateTo(MainViewIntent(), UserInterestIntent(), true)
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
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Text(text = stringResource(id = R.string.next), style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
fun PreviewUserInterest() {
}
