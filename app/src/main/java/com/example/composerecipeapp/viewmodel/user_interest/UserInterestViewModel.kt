package com.example.composerecipeapp.viewmodel.user_interest

import com.archerviewmodel.ArcherViewModel
import com.core.platform.functional.asConsumable
import com.example.composerecipeapp.core.functional.collectIn
import com.core.platform.usecase.None
import com.data.repository.datasource.SettingsDataStore
import com.domain.common.pojo.Cuisine
import com.domain.recipe.cuisines.GetSupportedCuisineUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInterestViewModel @Inject constructor(
    val cuisineUsecase: GetSupportedCuisineUsecase,
    val initialState: UserInterestState,
    val settingsDataStore: SettingsDataStore
) : ArcherViewModel<UserInterestState, UserInterestEvent>(initialState = initialState) {
    override fun onEvent(event: UserInterestEvent, state: UserInterestState) {
        when (event) {
            is LoadSupportedCuisine -> loadCuisines()
            is SelectedCuisine -> addCuisine(event.cuisine)
            is RemoveCuisine -> removeCuisine(event.cuisine)
            is UserInterestSelected -> onUserInterestSelected(state)
        }
    }

    private fun onUserInterestSelected(state: UserInterestState) {
        viewModelScope.launch {
            settingsDataStore.storeCuisine(state.cuisines.filter { it.isSelected }.map { it.name })
            setState {
                copy(viewEffect = OnCuisineSelected.asConsumable())
            }
        }
    }

    private fun addCuisine(cuisine: Cuisine) {
        setState {
            this.onCuisineSelected(cuisine = cuisine)
        }
    }

    private fun removeCuisine(cuisine: Cuisine) {
        setState {
            this.onCuisineRemoved(cuisine = cuisine)
        }
    }

    private fun loadCuisines() {
        cuisineUsecase(params = None).collectIn(viewModelScope) {
            setState {
                copy(cuisines = it.map { Cuisine(name = it) })
            }
        }
    }
}
