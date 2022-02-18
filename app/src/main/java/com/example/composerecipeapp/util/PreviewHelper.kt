package com.example.composerecipeapp.util

import com.archerviewmodel.ArcherViewModel
import com.archerviewmodel.events.ArcherEvent
import com.archerviewmodel.state.ArcherState
import com.example.composerecipeapp.domain.usecases.RecipeWithCuisine
import com.example.composerecipeapp.ui.pojo.RecipeModel

object PreviewHelper {
    val recipe = RecipeModel(
        1,
        title = "Creamy Ratatouille Over Penne",
        5,
        "https://spoonacular.com/recipeImages/Creamy-Ratatouille-640693.jpg",
        40,
        false
    )
    val recipe2 = RecipeModel(
        2,
        title = "Roasted Ratatouille Gratin",
        5,
        "https://spoonacular.com/recipeImages/Roasted-Ratatouille-Gratin-658641.jpg",
        40,
        true
    )
    val recipe3 = RecipeModel(
        3,
        title = "Ratatouille With Brie",
        5,
        "https://spoonacular.com/recipeImages/Ratatouille-With-Brie-657939.jpg",
        40,
        true
    )

    val recipeWithCuisine = RecipeWithCuisine(
        "Indian",
        listOf(recipe, recipe2, recipe3)
    )
}

class PreviewInitialState : ArcherState

class PreviewViewModel<S : ArcherState, E : ArcherEvent>(initialState: S) :
    ArcherViewModel<S, E>(initialState) {
    override fun onEvent(event: E, state: S) {

    }
}
