package com.example.composerecipeapp.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composerecipeapp.ui.Dispatch
import com.example.composerecipeapp.viewmodel.settings.ChangeDarkModeSettings
import com.example.composerecipeapp.viewmodel.settings.InitializeSettings
import com.example.composerecipeapp.viewmodel.settings.SettingsState
import com.example.composerecipeapp.viewmodel.settings.SettingsViewModel

@Composable
fun SettingsView() {
    val scaffoldState = rememberScaffoldState()
    val settingsViewModel : SettingsViewModel = hiltViewModel()
    settingsViewModel.dispatch(InitializeSettings)
    val state = settingsViewModel.stateEmitter.collectAsState().value
    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                DarkModeON(state.isDarkModeOn){
                    settingsViewModel.dispatch(ChangeDarkModeSettings(it))
                }
            }
        }
    )
}

@Preview
@Composable
fun SettingsPreview() {
    SettingsView()
}

@Composable
fun DarkModeON(isDarkModeOn : Boolean, dispatch: Dispatch<Boolean>) {
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
