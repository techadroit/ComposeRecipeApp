package com.feature.user.interest.viewmodel

import com.state_manager.managers.StateEventManager
import com.core.platform.usecase.None
import com.data.repository.datasource.SettingsDataStore
import com.domain.common.pojo.Cuisine
import com.domain.recipe.cuisines.GetSupportedCuisineUsecase
import com.state_manager.extensions.collectIn
import com.feature.user.interest.state.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserInterestViewModel @Inject constructor(
    val cuisineUsecase: GetSupportedCuisineUsecase,
    val initialInterestState: UserInterestState,
    val settingsDataStore: SettingsDataStore
) : StateEventManager<UserInterestState, UserInterestEvent>(initialState = initialInterestState) {
    override fun onEvent(event: UserInterestEvent, state: UserInterestState) {
        when (event) {
            is LoadSupportedCuisine -> loadCuisines()
            is SelectedCuisine -> addCuisine(event.cuisine)
            is RemoveCuisine -> removeCuisine(event.cuisine)
            is UserInterestSelected -> onUserInterestSelected(state)
        }
    }

    private fun onUserInterestSelected(state: UserInterestState) {
        coroutineScope.run {
            settingsDataStore.storeCuisine(state.cuisines.filter { it.isSelected }.map { it.name })
            setState {
                onUserInterestSelected()
            }
        }
    }

    private fun addCuisine(cuisine: Cuisine) {
        setState {
            onCuisineSelected(cuisine = cuisine)
        }
    }

    private fun removeCuisine(cuisine: Cuisine) {
        setState {
            onCuisineRemoved(cuisine = cuisine)
        }
    }

    private fun loadCuisines() {
        cuisineUsecase(params = None).collectIn(coroutineScope) {
            setState {
                copy(cuisines = it.map { Cuisine(name = it) })
            }
        }
    }
}
