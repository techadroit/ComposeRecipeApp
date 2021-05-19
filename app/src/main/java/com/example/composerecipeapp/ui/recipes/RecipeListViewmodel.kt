package com.recipeapp.view.viewmodel

import com.example.composerecipeapp.core.Consumable
import com.example.composerecipeapp.core.Resource
import com.example.composerecipeapp.core.collectIn
import com.example.composerecipeapp.core.exception.Failure
import com.example.composerecipeapp.core.network.NetworkHandler
import com.example.composerecipeapp.core.usecase.None
import com.example.composerecipeapp.core.viewmodel.BaseViewModel
import com.example.composerecipeapp.core.viewmodel.Event
import com.example.composerecipeapp.core.viewmodel.State
import com.example.composerecipeapp.data.repositories.RecipeLocalRepository
import com.example.composerecipeapp.data.repositories.RecipeRepository
import com.example.composerecipeapp.domain.usecases.SaveRecipeUsecase
import com.example.composerecipeapp.domain.usecases.SearchRecipeUsecase
import com.example.composerecipeapp.ui.pojo.RecipeModel
import com.example.composerecipeapp.util.QUERY
import com.recipeapp.core.network.api_service.RecipeApi
import kotlinx.coroutines.flow.catch

class RecipeListViewmodel(initialState: RecipeListState = RecipeListState()) :
    BaseViewModel<RecipeListState, RecipeEvent>(initialState) {

    lateinit var localRepository: RecipeLocalRepository
    var page = 1
    private val repos =
        RecipeRepository(NetworkHandler.getRetrofitInstance().create(RecipeApi::class.java))
    val searchUsecase by lazy { SearchRecipeUsecase(repos) }
    val usecase by lazy { SaveRecipeUsecase(localRepository) }

    fun saveRecipe(recipeModel: RecipeModel) =
        usecase(SaveRecipeUsecase.Param(recipeModel))
            .collectIn(viewModelScope) {
                onRecipeSaved()
            }


    private fun onRecipeSaved() {
        setState {
            copy(
                onSavedRecipes = Resource.Success(data = None),
                sideEffect = Consumable(SideEffect.OnSavedRecipe)
            )
        }
    }

    fun loadRecipes() =
        withState {
            if (!it.isLoading) {
                setState { copy(isLoading = true) }
                searchRecipe(query = QUERY)
            }
        }

    fun paginate() =
        withState {
            if (!it.isLoading) {
                page++
                setState { copy(isPaginate = true, isLoading = true) }
                searchRecipe(query = QUERY, isPaginate = true)
            }
        }

    private fun searchRecipe(query: String, isPaginate: Boolean = false) {
        searchUsecase(SearchRecipeUsecase.Param(query = query, offset = page))
            .catch { e ->
                handleFailure(e as Failure, isPaginate = isPaginate)
            }.collectIn(viewModelScope) {
                handleRecipeSearch(it, isPaginate)
            }
    }

    private fun handleFailure(failure: Failure, isPaginate: Boolean = false) {
        setState {
            copy(error = failure)
        }
    }

    private fun handleRecipeSearch(recipeList: List<RecipeModel>, isPaginate: Boolean) =
        setState {
            copy(
                isLoading = false,
                isPaginate = isPaginate,
                recipes = this.recipes + recipeList
            )
        }

    override fun onEvent(event: RecipeEvent) {
        when (event) {
            is LoadRecipes -> if (event.isPaginate) paginate() else loadRecipes()
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
        val newList = allRecipes + recipeList
        return RecipeData(newList)
    }
}

data class RecipeListState(
    val onSavedRecipes: Resource<*>? = Resource.Uninitialized,
    var recipes: RecipeData = RecipeData(emptyList()),
    var sideEffect: Consumable<SideEffect>? = null,
    val isLoading: Boolean = false,
    val isPaginate: Boolean = false,
    val error: Failure? = null
) : State

open class RecipeEvent : Event
data class LoadRecipes(val query: String = "chicken", var isPaginate: Boolean = false) :
    RecipeEvent()
