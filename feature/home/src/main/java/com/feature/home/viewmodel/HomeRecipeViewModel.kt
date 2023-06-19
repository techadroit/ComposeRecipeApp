package com.feature.home.viewmodel

import com.state_manager.managers.StateEventManager
import com.domain.recipe.cuisines.RecipesForSelectedCuisines
import com.state_manager.extensions.collectIn
import com.feature.home.state.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class HomeRecipeViewModel @Inject constructor(
    val recipeWithCuisine: RecipesForSelectedCuisines,
    private val initialState: HomeRecipeState
) : StateEventManager<HomeRecipeState, HomeRecipeEvent>(initialState) {

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
