package com.example.composerecipeapp.viewmodel.settings

import com.archerviewmodel.ArcherViewModel
import com.example.composerecipeapp.core.functional.collectIn
import com.example.composerecipeapp.data.datasource.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(initialState: SettingsState, var settingsDataStore: SettingsDataStore)
    : ArcherViewModel<SettingsState,SettingsEvent>(initialState){

    override fun onEvent(event: SettingsEvent, state: SettingsState) {
        when(event){
            is InitializeSettings -> initialize()
            is ChangeDarkModeSettings -> changeDataStoreSetting(event.isDarkModeOn)
        }
    }

    private fun changeDataStoreSetting(isDarkMode: Boolean){
        viewModelScope.launch {
            settingsDataStore.addDarkModeOn(isDarkMode)
        }
    }

    private fun initialize(){
        settingsDataStore.isDarkModeOn().collectIn(viewModelScope){
            setState {
                copy(isDarkModeOn = it)
            }
        }
    }
}
