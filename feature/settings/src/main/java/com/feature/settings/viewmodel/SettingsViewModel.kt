package com.feature.settings.viewmodel

import com.core.platform.usecase.None
import com.data.repository.datasource.SettingsDataStore
import com.domain.common.pojo.Cuisine
import com.domain.favourite.GetSavedRecipeCuisine
import com.feature.settings.state.SettingsState
import com.feature.settings.state.initialize
import com.feature.settings.state.onCuisineSaved
import com.feature.settings.state.onCuisineSelected
import com.state_manager.extensions.collectIn
import com.state_manager.extensions.pairOf
import com.state_manager.managers.StateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    initialState: SettingsState,
    var settingsDataStore: SettingsDataStore,
    var getSavedRecipeCuisine: GetSavedRecipeCuisine
) : StateManager<SettingsState>(initialState) {

    init {
        initialize()
    }

    fun saveCuisine() {
        withState {
            settingsDataStore.storeCuisine(it.list.filter { it.isSelected }.map { it.name })
            setState {
                onCuisineSaved()
            }
        }
    }

    fun cuisineSelected(cuisine: Cuisine) {
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

    fun cuisineDeSelected(cuisine: Cuisine) {
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

    fun changeDataStoreSetting(isDarkMode: Boolean) {
        coroutineScope.run {
            settingsDataStore.addDarkModeOn(isDarkMode)
        }
    }

    fun initialize() {
        settingsDataStore.isDarkModeOn().zip(getSavedRecipeCuisine(None)) { isDarkModeOn, list ->
            pairOf(isDarkModeOn, list)
        }.collectIn(coroutineScope) {
            setState {
                initialize(it.first, it.second)
            }
        }
    }

}
