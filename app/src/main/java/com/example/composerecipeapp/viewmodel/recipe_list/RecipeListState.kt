package com.example.composerecipeapp.viewmodel.recipe_list

import com.archerviewmodel.state.ArcherState
import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.functional.Consumable
import com.example.composerecipeapp.ui.pojo.RecipeModel

sealed class SideEffect {
    object OnSavedRecipe : SideEffect()
}

data class RecipeData(var allRecipes: List<RecipeModel>) {
    operator fun plus(recipeList: List<RecipeModel>): RecipeData {
        val newList = (allRecipes + recipeList).distinctBy { it.id }
        return RecipeData(newList)
    }
}

data class RecipeListState(
    var recipes: RecipeData = RecipeData(emptyList()),
    var sideEffect: Consumable<SideEffect>? = null,
    val isLoading: Boolean = false,
    val isPaginate: Boolean = false,
    val error: Failure? = null,
    val endOfItems: Boolean = false
) : ArcherState

fun RecipeListState.onRecipeSaved(id: Int): RecipeListState {
    val list = this.recipes.allRecipes.map {
        if (it.id == id)
            it.copy(isSaved = true)
        else
            it
    }
    return this.copy(
        recipes = RecipeData(list),
        sideEffect = Consumable(SideEffect.OnSavedRecipe)
    )
}

fun RecipeListState.onRecipeRemovedFromSavedList(id: Int): RecipeListState {
    val list = this.recipes.allRecipes.map {
        if (it.id == id)
            it.copy(isSaved = false)
        else
            it
    }
    return this.copy(
        recipes = RecipeData(list)
    )
}

fun RecipeListState.onRecipeLoad(
    isPaginate: Boolean,
    endOfItems: Boolean,
    recipeList: List<RecipeModel>
) = this.copy(
    isLoading = false,
    isPaginate = isPaginate,
    recipes = this.recipes + recipeList,
    endOfItems = endOfItems
)

fun RecipeListState.onLoading(isPaginate: Boolean = false) =
    this.copy(isLoading = true, isPaginate = isPaginate)

fun RecipeListState.onError(failure: Failure) = this.copy(error = failure)

fun RecipeListState.onSaved(recipeId: Int) {
    val list = this.recipes.allRecipes
    list.first { it.id == recipeId }
    this.copy(
        recipes = RecipeData(list)
    )
}
