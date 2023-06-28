package com.feature.home.viewmodel

import com.state_manager.managers.StateEventManager
import com.domain.recipe.cuisines.RecipesForSelectedCuisines
import com.state_manager.extensions.collectIn
import com.feature.home.state.*
import com.state_manager.scopes.StateManagerCoroutineScope
import com.state_manager.scopes.StateManagerCoroutineScopeImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class HomeRecipeViewModel @Inject constructor(
    val recipeWithCuisine: RecipesForSelectedCuisines,
    private val initialHomeState: HomeRecipeState,
    val scope: StateManagerCoroutineScope = StateManagerCoroutineScopeImpl(Dispatchers.Default + SupervisorJob())
) : StateEventManager<HomeRecipeState, HomeRecipeEvent>(initialHomeState,scope) {

    init {
        dispatch(LoadRecipeEvent)
    }
    override fun onEvent(event: HomeRecipeEvent, state: HomeRecipeState) {
        when (event) {
            is LoadRecipeEvent -> loadRecipes()
            is RefreshHomeEvent -> refresh()
            is ViewAllRecipes -> onViewAllRecipes(event.cuisine)
            is ViewRecipeDetail -> onViewRecipeDetail(event.recipeId)
        }
    }

    private fun refresh() {
        setState {
            initialState()
        }
        loadRecipes()
    }

    private fun onViewRecipeDetail(recipeId: String) {
        setState {
            viewDetail(recipeId)
        }
    }

    private fun onViewAllRecipes(cuisine: String) {
        setState {
            viewAll(cuisine)
        }
    }

    private fun loadRecipes() {
        setState {
            showLoading(true)
        }
        recipeWithCuisine()
            .catch {
                setState {
                    onLoadingError()
                }
            }
            .collectIn(coroutineScope) {
            setState {
                add(it)
                    .showLoading(false)
            }
        }
    }
}
