package com.example.composerecipeapp.ui.recipe_list

import com.example.composerecipeapp.core.Consumable
import com.example.composerecipeapp.core.collectIn
import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.viewmodel.AppEvent
import com.example.composerecipeapp.core.viewmodel.AppState
import com.example.composerecipeapp.core.viewmodel.BaseViewModel
import com.example.composerecipeapp.domain.usecases.SaveRecipeUsecase
import com.example.composerecipeapp.domain.usecases.SearchRecipeUsecase
import com.example.composerecipeapp.ui.pojo.RecipeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewmodel @Inject constructor(
    initialState: RecipeListState, val savedRecipeUsecase: SaveRecipeUsecase,
    val searchUsecase: SearchRecipeUsecase
) :
    BaseViewModel<RecipeListState, RecipeEvent>(initialState) {

    var page = 1
    fun saveRecipe(recipeModel: RecipeModel) =
        savedRecipeUsecase(SaveRecipeUsecase.Param(recipeModel))
            .collectIn(viewModelScope) {
                onRecipeSaved()
            }

    private fun onRecipeSaved() {
        setState {
            copy(
                sideEffect = Consumable(SideEffect.OnSavedRecipe)
            )
        }
    }

    private fun loadRecipes(query: String) =
        withState {
            if (!it.isLoading) {
                setState { this.onLoading() }
                searchRecipe(query = query)
            }
        }

    private fun paginate(query: String) =
        withState {
            if (!it.isLoading) {
                page++
                setState { this.onLoading(true) }
                searchRecipe(query = query, isPaginate = true)
            }
        }

    private fun searchRecipe(query: String, isPaginate: Boolean = false) {
        searchUsecase(SearchRecipeUsecase.Param(query = query, offset = page))
            .catch { e ->
                handleFailure(e as Failure, isPaginate = isPaginate)
            }.collectIn(viewModelScope) {
                handleRecipeSearch(it.first, isPaginate, it.second)
            }
    }

    private fun handleFailure(failure: Failure, isPaginate: Boolean = false) {
        setState {
            this.onError(failure = failure)
        }
    }

    private fun handleRecipeSearch(
        recipeList: List<RecipeModel>,
        isPaginate: Boolean,
        endOfItems: Boolean
    ) =
        setState {
            this.onRecipeLoad(isPaginate, endOfItems, recipeList)
        }

    override fun onEvent(event: RecipeEvent, state: RecipeListState) {
        when (event) {
            is LoadRecipes -> if (event.isPaginate) paginate(event.query) else loadRecipes(event.query)
            is SaveRecipeEvent -> saveRecipe(event.recipeModel)
            is RemoveSavedRecipeEvent -> {}
            else -> {
            }
        }
    }
}

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
) : AppState

interface RecipeEvent : AppEvent
data class LoadRecipes(val query: String, var isPaginate: Boolean = false) :
    RecipeEvent

data class SaveRecipeEvent(val recipeModel: RecipeModel) : RecipeEvent

data class RemoveSavedRecipeEvent(val recipeModel: RecipeModel) : RecipeEvent

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
