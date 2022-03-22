package com.example.composerecipeapp.viewmodel.main

import com.archerviewmodel.ArcherViewModel
import com.archerviewmodel.extensions.collectIn
import com.example.composerecipeapp.data.datasource.SettingsDataStore
import com.example.composerecipeapp.data.repositories.RecipeLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    initialState: MainViewState,
    val settingsDataStore: SettingsDataStore,
    val localRepository: RecipeLocalRepository
) :
    ArcherViewModel<MainViewState, MainViewEvent>(initialState = initialState) {

    val counter = MutableStateFlow(0L)
    val showBadge = MutableStateFlow(false)
    var currentIndex = 0

    fun onScreenChange(index: Int) {
        currentIndex = index
    }

    override fun onEvent(event: MainViewEvent, state: MainViewState) {
        when (event) {
            is LoadSettings -> loadSettings()
        }
    }

    init {
        countSavedItems()
    }

    private fun countSavedItems() {
        localRepository.getSavedRecipesCount().collectIn(viewModelScope) {
            counter.value = it
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
