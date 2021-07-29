package com.example.composerecipeapp.viewmodel.home_recipes

import com.archerviewmodel.ArcherViewModel
import com.example.composerecipeapp.core.functional.collectIn
import com.example.composerecipeapp.core.usecase.None
import com.example.composerecipeapp.domain.usecases.RecipeWithCuisine
import com.example.composerecipeapp.domain.usecases.RecipesForSelectedCuisines
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeRecipeViewModel @Inject constructor(val recipeWithCuisine: RecipesForSelectedCuisines,
                                              val initialState: HomeRecipeState) : ArcherViewModel<HomeRecipeState,HomeRecipeEvent>(initialState) {
    override fun onEvent(event: HomeRecipeEvent, state: HomeRecipeState) {
        when(event){
            is LoadRecipeEvent -> loadRecipes()
        }
    }

    private fun loadRecipes(){
        recipeWithCuisine(None).collectIn(viewModelScope){
            setState {
                this.onLoad(it)
            }
        }
    }
}
