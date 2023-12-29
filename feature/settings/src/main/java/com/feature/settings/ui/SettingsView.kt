package com.feature.settings.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.feature.common.Dispatch
import com.feature.common.ui.extension.contentWidth
import com.feature.settings.R
import com.feature.settings.viewmodel.SettingsViewModel
import com.feature.user.interest.ui.CuisineList
import com.state_manager.ui.observeState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalFoundationApi
@Composable
fun SettingsView() {
    val scope = rememberCoroutineScope()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val snackBarHostState = remember { SnackbarHostState() }

    settingsViewModel.observeState { state ->

        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) },
            content = {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(id = R.string.settings),
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    DarkModeON(state.isDarkModeOn) {
                        settingsViewModel.changeDataStoreSetting(it)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CuisineList(
                            modifier = Modifier.contentWidth().align(Alignment.Center),
                            cuisines = state.list,
                            selectionCount = 5
                        ) { it, cuisine ->
                            if (it)
                                settingsViewModel.cuisineSelected(cuisine)
                            else
                                settingsViewModel.cuisineDeSelected(cuisine)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    if (state.enableSaveOptions)
                        SaveButton(modifier = Modifier.align(Alignment.End)) {
                            settingsViewModel.saveCuisine()
                        }
                }
            }
        )

        state.viewEffect?.consume()?.let {
            scope.launch {
                snackBarHostState.showSnackbar(message = "Settings Saved Successfully")
            }
        }
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
            colors = SwitchDefaults.colors(
                checkedTrackColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            checked = isDarkModeOn,
            onCheckedChange = {
                dispatch(it)
            }
        )
    }
}

@Composable
fun SaveButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton(
        shape = ButtonDefaults.filledTonalShape,
        onClick = {
            onClick()
        },
        modifier = modifier,
    ) {
        Text(
            text = stringResource(id = R.string.save),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
fun SavePreview() {
    SaveButton {
    }
}
