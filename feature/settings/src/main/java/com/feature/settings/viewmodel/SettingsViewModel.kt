package com.feature.settings.viewmodel

import androidx.lifecycle.viewModelScope
import com.core.platform.usecase.None
import com.data.repository.datasource.SettingsDataStore
import com.domain.common.pojo.Cuisine
import com.domain.favourite.GetSavedRecipeCuisine
import com.feature.settings.state.SettingsState
import com.feature.settings.state.deselectCuisine
import com.feature.settings.state.initialize
import com.feature.settings.state.onCuisineSaved
import com.feature.settings.state.onCuisineSelected
import com.state_manager.extensions.collectInScope
import com.state_manager.extensions.pairOf
import com.state_manager.managers.StateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
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
            setState {
                onCuisineSelected(cuisine,list)
            }
        }
    }

    fun cuisineDeSelected(cuisine: Cuisine) {
        withState {
            setState {
                deselectCuisine(cuisine)
            }
        }
    }

    fun changeDataStoreSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            settingsDataStore.addDarkModeOn(isDarkMode)
        }
    }

    fun initialize() {
        settingsDataStore.isDarkModeOn().zip(getSavedRecipeCuisine(None)) { isDarkModeOn, list ->
            pairOf(isDarkModeOn, list)
        }.collectInScope(viewModelScope) {
            setState {
                initialize(it.first ?: false, it.second)
            }
        }
    }

}
