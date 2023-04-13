package com.example.composerecipeapp.ui.user_interest

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composerecipeapp.R
import com.example.composerecipeapp.platform.navigation.navigator.AppMainNavigation
import com.example.composerecipeapp.platform.navigation.screens.MainViewIntent
import com.example.composerecipeapp.platform.navigation.screens.UserInterestIntent
import com.example.composerecipeapp.ui.theme.UserInterestComposable
import com.example.composerecipeapp.ui.theme.primaryColorDark
import com.example.composerecipeapp.ui.views.CuisineList
import com.example.composerecipeapp.util.observeState
import com.example.composerecipeapp.viewmodel.user_interest.*

@ExperimentalFoundationApi
@Composable
fun UserInterest(appMainNavigation: AppMainNavigation) {
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
        appMainNavigation.navigateTo(MainViewIntent(), UserInterestIntent(), true)
//        navController.navigate(MainViewIntent.getScreenName()) {
//            popUpTo(UserInterestIntent.getScreenName()) {
//                inclusive = true
//            }
//        }
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
        Text(text = stringResource(id = R.string.next), style = MaterialTheme.typography.body1)
    }
}

@Preview
@Composable
fun PreviewUserInterest() {
}
