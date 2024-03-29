package com.example.composerecipeapp.viewmodel.main

import androidx.lifecycle.viewModelScope
import com.data.repository.datasource.SettingsDataStore
import com.state_manager.extensions.collectInScope
import com.state_manager.managers.StateEventManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    initialState: MainViewState,
    val settingsDataStore: SettingsDataStore
) :
    StateEventManager<MainViewState, MainViewEvent>(initialState = initialState) {

    override fun onEvent(event: MainViewEvent, state: MainViewState) {
        when (event) {
            is LoadSettings -> loadSettings()
        }
    }

    private fun loadSettings() {
        settingsDataStore.getCuisines().zip(settingsDataStore.isDarkModeOn()) { list, darkMode ->
            MainViewState(isDarkModeOn = darkMode, showLandingScreen = list.isEmpty())
        }.collectInScope(viewModelScope) {
            setState {
                it
            }
        }
    }
}
