package com.feature.user.interest.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.core.themes.ComposeRecipeAppTheme
import com.feature.common.observeSideEffect
import com.feature.common.observeState
import com.feature.common.ui.extension.contentWidth
import com.feature.user.interest.R
import com.feature.user.interest.state.LoadSupportedCuisine
import com.feature.user.interest.state.RemoveCuisine
import com.feature.user.interest.state.SelectedCuisine
import com.feature.user.interest.state.UserInterestSelected
import com.feature.user.interest.viewmodel.UserInterestViewModel
import com.recipe.app.navigation.intent.MainViewIntent
import com.recipe.app.navigation.intent.UserInterestIntent
import com.recipe.app.navigation.provider.ParentNavHostController

@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.UserInterestScreen() {
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

    ComposeRecipeAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.TopStart),
                    text = stringResource(id = R.string.select_cuisine),
                    style = MaterialTheme.typography.displayLarge
                )
                CuisineList(
                    cuisines = state.cuisines,
                    modifier = Modifier
                        .contentWidth()
                        .align(Alignment.Center),
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

    viewModel.observeSideEffect {
        topLevelNavigator.navigateTo(MainViewIntent(), UserInterestIntent(), true)
    }

    LaunchedEffect(true) {
        viewModel.dispatch(LoadSupportedCuisine)
    }
}

@Composable
fun NextButton(modifier: Modifier, onClick: () -> Unit) {
    OutlinedButton(
        onClick = {
            onClick()
        },
        modifier = modifier,
    ) {
        Text(text = stringResource(id = R.string.next), style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
fun PreviewUserInterest() {
}
