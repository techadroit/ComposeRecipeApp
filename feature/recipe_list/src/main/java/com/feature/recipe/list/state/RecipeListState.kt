package com.feature.recipe.list.state

import com.state_manager.state.AppState
import com.core.platform.exception.Failure
import com.core.platform.functional.Consumable
import com.core.platform.functional.ViewEffect
import com.domain.common.pojo.RecipeModel
import com.state_manager.side_effects.SideEffect

object OnSavedRecipe : SideEffect

data class RecipeData(var allRecipes: List<RecipeModel>) {
    operator fun plus(recipeList: List<RecipeModel>): RecipeData {
        val newList = (allRecipes + recipeList).distinctBy { it.id }
        return RecipeData(newList)
    }
}

data class RecipeListState(
    var recipes: RecipeData = RecipeData(emptyList()),
    var viewEffect: Consumable<ViewEffect>? = null,
    val isLoading: Boolean = false,
    val isPaginate: Boolean = false,
    val error: Failure? = null,
    val endOfItems: Boolean = false,
    val selectedQuery: String? = null
) : AppState

fun RecipeListState.onRecipeSaved(id: Int): RecipeListState {
    val list = this.recipes.allRecipes.map {
        it.copy(isSaved = it.id == id)
    }
    return this.copy(
        recipes = RecipeData(list)
    )
}

fun RecipeListState.onRecipeRemovedFromSavedList(id: Int): RecipeListState {
    val list = this.recipes.allRecipes.map {
            it.copy(isSaved = it.id == id)
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

fun RecipeListState.onLoading(isPaginate: Boolean = false, query: String? = null) =
    this.copy(isLoading = true, isPaginate = isPaginate, selectedQuery = query)

fun RecipeListState.onError(failure: Failure) = this.copy(error = failure)

fun RecipeListState.showLoading() = isLoading && !isPaginate

fun RecipeListState.showPagination() = isLoading && isPaginate