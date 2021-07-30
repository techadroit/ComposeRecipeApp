package com.example.composerecipeapp.viewmodel.settings

import com.archerviewmodel.ArcherViewModel
import com.example.composerecipeapp.core.functional.asConsumable
import com.example.composerecipeapp.core.functional.collectIn
import com.example.composerecipeapp.core.functional.pairOf
import com.example.composerecipeapp.core.usecase.None
import com.example.composerecipeapp.data.datasource.SettingsDataStore
import com.example.composerecipeapp.domain.usecases.GetSavedRecipeCuisine
import com.example.composerecipeapp.viewmodel.user_interest.Cuisine
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
                copy(sideEffect = CuisinePreferencesSaved.asConsumable())
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
                copy(list = list, enableSaveOptions = enableSaveOptions(list))
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
                copy(list = list, enableSaveOptions = enableSaveOptions(list))
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
                copy(
                    isDarkModeOn = it.first,
                    list = it.second,
                    enableSaveOptions = enableSaveOptions(list)
                )
            }
        }
    }

    private fun enableSaveOptions(list: List<Cuisine>) = list.count { it.isSelected } == 5

}
