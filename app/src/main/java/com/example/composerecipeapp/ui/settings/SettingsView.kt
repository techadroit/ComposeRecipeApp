package com.example.composerecipeapp.ui.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composerecipeapp.R
import com.example.composerecipeapp.ui.util.Dispatch
import com.example.composerecipeapp.ui.views.CuisineList
import com.example.composerecipeapp.util.observeState
import com.example.composerecipeapp.viewmodel.settings.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalFoundationApi
@Composable
fun SettingsView() {
    val scope = rememberCoroutineScope()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val state = settingsViewModel.observeState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(id = R.string.settings),
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                DarkModeON(state.isDarkModeOn) {
                    settingsViewModel.dispatch(ChangeDarkModeSettings(it))
                }
                Spacer(modifier = Modifier.height(8.dp))
                CuisineList(cuisines = state.list, selectionCount = 5) { it, cuisine ->
                    settingsViewModel.dispatch(
                        if (it) CuisineSelected(cuisine)
                        else CuisineDeSelected(cuisine)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (state.enableSaveOptions)
                    SaveButton(modifier = Modifier.align(Alignment.End)) {
                        settingsViewModel.dispatch(SaveCuisine)
                    }
            }
        }
    )

    state.viewEffect?.consume()?.let {
        scope.launch {
            snackbarHostState.showSnackbar(message = "Settings Saved Successfully")
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
        Text(
            text = stringResource(id = R.string.dark_mode),
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isDarkModeOn,
            onCheckedChange = {
                dispatch(it)
            }
        )
    }
}

@Composable
fun SaveButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier,
    ) {
        Text(text = stringResource(id = R.string.save), style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
fun SavePreview() {
    SaveButton {
    }
}
