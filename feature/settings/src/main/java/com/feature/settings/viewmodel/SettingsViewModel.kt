package com.feature.settings.viewmodel

import com.archerviewmodel.ArcherViewModel
import com.core.platform.usecase.None
import com.data.repository.datasource.SettingsDataStore
import com.domain.common.pojo.Cuisine
import com.domain.favourite.GetSavedRecipeCuisine
import com.example.composerecipeapp.core.functional.collectIn
import com.example.composerecipeapp.core.functional.pairOf
import com.feature.settings.state.ChangeDarkModeSettings
import com.feature.settings.state.CuisineDeSelected
import com.feature.settings.state.CuisineSelected
import com.feature.settings.state.InitializeSettings
import com.feature.settings.state.SaveCuisine
import com.feature.settings.state.SettingsEvent
import com.feature.settings.state.SettingsState
import com.feature.settings.state.initialize
import com.feature.settings.state.onCuisineSaved
import com.feature.settings.state.onCuisineSelected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    initialState: SettingsState,
    var settingsDataStore: SettingsDataStore,
    var getSavedRecipeCuisine: GetSavedRecipeCuisine
) : ArcherViewModel<SettingsState, SettingsEvent>(initialState) {

    override fun onEvent(event: SettingsEvent, state: SettingsState) {
        when (event) {
            is InitializeSettings -> initialize()
            is ChangeDarkModeSettings -> changeDataStoreSetting(event.isDarkModeOn)
            is CuisineDeSelected -> cuisineDeSelected(event.cuisine)
            is CuisineSelected -> cuisineSelected(event.cuisine)
            is SaveCuisine -> saveCuisine()
        }
    }

    private fun saveCuisine() {
        withState {
            settingsDataStore.storeCuisine(it.list.filter { it.isSelected }.map { it.name })
            setState {
                onCuisineSaved()
            }
        }
    }

    private fun cuisineSelected(cuisine: Cuisine) {
        withState {
            val list = it.list.map {
                if (it == cuisine) {
                    it.copy(isSelected = true)
                } else {
                    it
                }
            }
            setState {
                onCuisineSelected(list)
            }
        }
    }

    private fun cuisineDeSelected(cuisine: Cuisine) {
        withState {
            val list = it.list.map {
                if (it == cuisine) {
                    it.copy(isSelected = false)
                } else {
                    it
                }
            }
            setState {
                onCuisineSelected(list = list)
            }
        }
    }

    private fun changeDataStoreSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            settingsDataStore.addDarkModeOn(isDarkMode)
        }
    }

    private fun initialize() {
        settingsDataStore.isDarkModeOn().zip(getSavedRecipeCuisine(None)) { isDarkModeOn, list ->
            pairOf(isDarkModeOn, list)
        }.collectIn(viewModelScope) {
            setState {
                initialize(it.first, it.second)
            }
        }
    }

}
