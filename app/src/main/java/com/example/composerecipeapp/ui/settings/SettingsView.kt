package com.example.composerecipeapp.ui.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composerecipeapp.ui.Dispatch
import com.example.composerecipeapp.ui.views.CuisineList
import com.example.composerecipeapp.util.observeState
import com.example.composerecipeapp.viewmodel.settings.*
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun SettingsView() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val state = settingsViewModel.observeState()

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                DarkModeON(state.isDarkModeOn) {
                    settingsViewModel.dispatch(ChangeDarkModeSettings(it))
                }
                Spacer(modifier = Modifier.height(8.dp))
                CuisineList(cuisines = state.list,selectionCount = 5) { it, cuisine ->
                    settingsViewModel.dispatch(
                        if (it) CuisineSelected(cuisine)
                        else CuisineDeSelected(cuisine)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                if(state.enableSaveOptions)
                SaveButton(modifier = Modifier.align(Alignment.End)) {
                    settingsViewModel.dispatch(SaveCuisine)
                }
            }
        }
    )

    state.sideEffect?.consume()?.let {
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(message = "Settings Saved Successfully")
        }
    }

    LaunchedEffect(true) {
        settingsViewModel.dispatch(InitializeSettings)
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun SettingsPreview() {
    SettingsView()
}

@Composable
fun DarkModeON(isDarkModeOn: Boolean, dispatch: Dispatch<Boolean>) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Dark Mode", style = MaterialTheme.typography.h1)
        Spacer(modifier = Modifier.weight(1f))
        Switch(checked = isDarkModeOn, onCheckedChange = {
            dispatch(it)
        })
    }
}

@Composable
fun SaveButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier,
//        colors =
//        ButtonDefaults.buttonColors(
//            backgroundColor = MaterialTheme.colors.secondary,
//            contentColor = MaterialTheme.colors.onSurface
//        )
    ) {
        Text("Save", style = MaterialTheme.typography.body1)
    }
}

@Preview
@Composable
fun SavePreview() {
    SaveButton {

    }
}
