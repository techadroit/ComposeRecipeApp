package com.example.composerecipeapp.viewmodel.home_recipes

import com.archerviewmodel.ArcherViewModel
import com.example.composerecipeapp.core.functional.asConsumable
import com.example.composerecipeapp.core.functional.collectIn
import com.example.composerecipeapp.domain.usecases.RecipesForSelectedCuisines
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeRecipeViewModel @Inject constructor(
    val recipeWithCuisine: RecipesForSelectedCuisines,
    val initialState: HomeRecipeState
) : ArcherViewModel<HomeRecipeState, HomeRecipeEvent>(initialState) {
    override fun onEvent(event: HomeRecipeEvent, state: HomeRecipeState) {
        when (event) {
            is LoadRecipeEvent -> loadRecipes()
            is RefreshHomeEvent -> refresh()
            is ViewAllRecipes -> onViewAllRecipes(event.cuisine)
            is ViewRecipeDetail -> onViewRecipeDetail(event.recipeId)
        }
    }

    fun refresh() {
        val newState = HomeRecipeState()
        setState {
            newState
        }
        loadRecipes()
    }

    private fun onViewRecipeDetail(recipeId: String) {
        setState {
            copy(viewEffect = ViewRecipesDetailViewEffect(recipeId).asConsumable())
        }
    }

    private fun onViewAllRecipes(cuisine: String) {
        setState {
            copy(viewEffect = ViewAllViewEffect(cuisine).asConsumable())
        }
    }

    private fun loadRecipes() {
        recipeWithCuisine().collectIn(viewModelScope) {
            setState {
                this.add(it)
            }
        }
    }
}
