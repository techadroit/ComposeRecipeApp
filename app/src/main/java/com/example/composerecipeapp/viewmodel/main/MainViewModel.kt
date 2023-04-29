package com.example.composerecipeapp.viewmodel.main

import com.archerviewmodel.ArcherViewModel
import com.example.composerecipeapp.core.functional.collectIn
import com.data.repository.datasource.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    initialState: MainViewState,
    val settingsDataStore: SettingsDataStore
) :
    ArcherViewModel<MainViewState, MainViewEvent>(initialState = initialState) {

    override fun onEvent(event: MainViewEvent, state: MainViewState) {
        when (event) {
            is LoadSettings -> loadSettings()
        }
    }

    private fun loadSettings() {
        settingsDataStore.getCuisines().zip(settingsDataStore.isDarkModeOn()) { list, darkMode ->
            MainViewState(isDarkModeOn = darkMode, showLandingScreen = list.isEmpty())
        }.collectIn(viewModelScope) {
            setState {
                it
            }
        }
    }
}
